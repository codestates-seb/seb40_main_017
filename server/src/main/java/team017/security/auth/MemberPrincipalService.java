// package team017.security.auth;
//
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import team017.member.entity.Member;
// import team017.member.repository.MemberRepository;
// import team017.security.auth.MemberPrincipal;
//
// @Service
// @RequiredArgsConstructor
// public class MemberPrincipalService implements UserDetailsService {
// 	private final MemberRepository memberRepository;
//
// 	@Override
// 	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
// 		Member member = memberRepository.findMemberByEmail(username);
// 		if (member == null) {
// 			throw new UsernameNotFoundException("회원이 존재하지 않습니다.");
// 		}
//
// 		return MemberPrincipal.create(member);
// 	}
// }
