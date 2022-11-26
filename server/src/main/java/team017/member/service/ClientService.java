package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.dto.ClientPatchDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.repository.ClientRepository;
import team017.security.utils.SecurityUtil;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ClientService {
	private final ClientRepository clientRepository;
	private final MemberService memberService;
	private final MemberMapper mapper;

	/* 존재하는 소비자인지 확인 + 소비자 정보 리턴 */
	public Client findVerifiedClient(long clientId) {
		Optional<Client> optionalClient = clientRepository.findById(clientId);
		Client findClient = optionalClient.orElseThrow(() -> new RuntimeException("Client Not Found"));
		return findClient;
	}

	/* 소비자 조회 */
	@Transactional(readOnly = true)
	public Client findClient(long clientId) {
		// correctClient(clientId);
		Client client = findVerifiedClient(clientId);

		return client;
	}

	/* 소비자 정보 수정 */
	public Client updateClient(long clientId, ClientPatchDto patch) {
		Client client = findClient(clientId);
		long memberId = client.getMember().getMemberId();
		Member member = memberService.updateMember(memberId, mapper.clientPatchDtoToMember(patch));

		clientRepository.save(client);

		return client;
	}

	/* 로그인 한 사용자와 접근 사용자 판별 */
	public void correctClient(long accessId) {
		String email = SecurityUtil.getCurrentEmail();
		Member loginMember = memberService.findMemberByEmail(email);
		log.info("로그인한 유저 : {}", loginMember.getName());

		if (loginMember.getClient().getClientId() != accessId) {
			throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS);
		}
	}
}
