package team017.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import team017.member.entity.Member;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class MemberDetail implements UserDetails, OAuth2User {

	private final String email;
	private final String name;
	private final String password;
	private final Collection<GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	/* MemberDetail Create Method */
	public static MemberDetail create(Member member) {
		return new MemberDetail(
			member.getEmail(),
			member.getName(),
			member.getPassword(),
			Collections.singletonList(new SimpleGrantedAuthority(member.getRoles().get(0)))
		);
	}

	public static MemberDetail create(Member member, Map<String, Object> attributes) {
		MemberDetail memberDetail = create(member);
		memberDetail.setAttributes(attributes);

		return memberDetail;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/* 로그인에서 UserName 은 이메일 */
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
