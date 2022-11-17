package team017.security.jwt.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team017.member.entity.Member;
import team017.member.repository.MemberRepository;
import team017.security.utils.CustomAuthorityUtils;

public class LoginDto {

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Request {
		private String username;
		private String password;

		public Member toMember(PasswordEncoder passwordEncoder) {
			return Member.builder()
				.email(username)
				.password(passwordEncoder.encode(password))
				.build();
		}

		public UsernamePasswordAuthenticationToken toAuthentication() {
			return new UsernamePasswordAuthenticationToken(username, password);
		}
	}

	// @Getter
	// @AllArgsConstructor
	// @NoArgsConstructor
	// public static class Response {
	// 	private long memberId;
	//
	// 	public static Response of(Member member) {
	// 		return new Response(member.getMemberId());
	// 	}
	// }
}
