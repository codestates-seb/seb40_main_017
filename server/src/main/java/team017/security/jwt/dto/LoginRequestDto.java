package team017.security.jwt.dto;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team017.member.entity.Member;
import team017.member.repository.MemberRepository;
import team017.security.utils.CustomAuthorityUtils;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {

	private String email;
	private String password;

	public Member toMember(PasswordEncoder passwordEncoder) {
		return Member.builder()
			.email(email)
			.password(passwordEncoder.encode(password))
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(email, password);
	}
}
