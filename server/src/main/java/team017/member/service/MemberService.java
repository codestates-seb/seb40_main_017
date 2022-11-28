package team017.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.ProviderType;
import team017.member.entity.Seller;
import team017.member.repository.MemberRepository;
import team017.security.utils.CustomAuthorityUtils;

@Service
@Slf4j
@Transactional
public class MemberService {

	private final MemberRepository memberRepository;
	private final CustomAuthorityUtils authorityUtils;
	private final PasswordEncoder passwordEncoder;

	public MemberService(MemberRepository memberRepository, CustomAuthorityUtils authorityUtils,
		PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.authorityUtils = authorityUtils;
		this.passwordEncoder = passwordEncoder;
	}

	/* 회원 가입 */
	public Member createMember(Member member) {
		verifyEmailExist(member.getEmail());
		correctRole(member.getRole());
		List<String> roles = authorityUtils.createRoles(member.getRole());
		if (member.getRole().equalsIgnoreCase("client")) {
			member.setClient(new Client());
		}
		if (member.getRole().equalsIgnoreCase("seller")) {
			member.setSeller(new Seller());
			member.getSeller().setIntroduce("안녕하세요, " + member.getName() + "입니다.");
			member.getSeller().setImageUrl("https://jihoon-bucket.s3.ap-northeast-2.amazonaws.com/%E1%84%89%E1%85%A2%E1%86%BC%E1%84%89%E1%85%A1%E1%86%AB%E1%84%8C%E1%85%A1%E1%84%80%E1%85%B5%E1%84%87%E1%85%A9%E1%86%AB%E1%84%8B%E1%85%B5%E1%84%86%E1%85%B5%E1%84%8C%E1%85%B5.png");
		}

		/* 비밀번호 암호화 */
		String encryptedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encryptedPassword);

		/* 대문자로 저장 */
		member.setRole(member.getRole().toUpperCase());
		member.setRoles(roles);
		member.setProviderType(ProviderType.LOCAL);

		return memberRepository.save(member);
	}

	/* 회원 수정 (멤버 파트) */
	public Member updateMember(long memberId, Member member) {

		Member findMember = findVerifiedMember(memberId);

		Optional.ofNullable(member.getName()).ifPresent(name -> findMember.setName(name));
		Optional.ofNullable(member.getPhone()).ifPresent(phone -> findMember.setPhone(phone));
		Optional.ofNullable(member.getAddress()).ifPresent(address -> findMember.setAddress(address));
		Optional.ofNullable(member.getPassword())
			.ifPresent(password -> findMember.setPassword(passwordEncoder.encode(password)));

		return memberRepository.save(findMember);
	}


	/* 회원 탈퇴 */
	public void deleteMember(long memberId) {
		Member member = findVerifiedMember(memberId);
		memberRepository.delete(member);
	}

	/* 존재하는 이메일인지 확인 */
	public void verifyEmailExist(String email) {
		Optional<Member> optionalMember = memberRepository.findByEmail(email);
		if (optionalMember.isPresent()) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
		}
	}


	/* 존재하는 회원인지 확인 */
	public Member findVerifiedMember(long memberId) {
		Optional<Member> optionalMember = memberRepository.findById(memberId);
		Member findMember = optionalMember.orElseThrow(
			() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

		return findMember;
	}

	/* 비밀번호 확인 */
	public void correctPassword(String password, String passwordCheck) {
		if (!password.equals(passwordCheck)) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);
		}
	}

	/* 역할 확인 */
	private void correctRole(String target) {
		if (! target.equalsIgnoreCase("CLIENT") && ! target.equalsIgnoreCase("SELLER")) {
			throw new BusinessLogicException(ExceptionCode.ROLE_ERROR);
		}
	}

	/* 로컬 프로바이더 확인 */
	public void checkLocalProvider(ProviderType providerType) {
		if (providerType != ProviderType.LOCAL) {
			throw new BusinessLogicException(ExceptionCode.PROVIDER_ERROR);
		}
	}

	/* 이름 가져오기 */
	public String getMemberName(Long memberId){
		Member member = findVerifiedMember(memberId);
		String foundMemberName = member.getName();

		return foundMemberName;
	}

	/* 이메일로 멤버 가져오기 */
	public Member findMemberByEmail(String email) {
		Member member = memberRepository.findMemberByEmail(email);
		if (member == null) {
			throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
		}

		return member;
	}
}
