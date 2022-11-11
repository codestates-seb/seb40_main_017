package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Member;
import team017.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	/* 회원 가입 */
	public Member createMember(Member member) {
		verifyEmailExist(member.getEmail());
		/* 역할 및 비밀번호 암호화는 테스트 후에 추가 예정 */

		return memberRepository.save(member);
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
}