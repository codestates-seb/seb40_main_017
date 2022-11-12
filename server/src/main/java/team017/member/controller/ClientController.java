package team017.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team017.member.dto.MemberDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.ClientService;
import team017.member.service.MemberService;

/* 소비자 관련 컨트롤러 : 마이페이지 조회, 정보 수정 */
@RestController
@RequestMapping("members/client")
@Valid
@RequiredArgsConstructor
public class ClientController {
	private final ClientService clientService;
	private final MemberService memberService;
	private final MemberMapper mapper;

	/* 소비자 마이 페이지 조회 */
	@GetMapping("/{client_id}")
	public ResponseEntity getClient(@PathVariable("client_id") @Positive long clientId) {
		Client client = clientService.findVerifiedClient(clientId);
		Member member = memberService.findVerifiedMember(client.getMember().getMemberId());
		MemberDto.ClientDto response = mapper.memberToClientDto(member, client);

		return ResponseEntity.ok(response);
	}
}
