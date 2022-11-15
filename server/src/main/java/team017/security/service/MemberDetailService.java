package team017.security.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Member;
import team017.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> optionalMember = memberRepository.findByEmail(username);
		Member findMember =
			optionalMember.orElseThrow(() -> new UsernameNotFoundException("해당하는 회원을 찾을 수 없습니다."));

		return MemberDetail.create(findMember);
	}
}
