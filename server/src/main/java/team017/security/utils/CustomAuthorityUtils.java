package team017.security.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import team017.member.dto.MemberDto;

@Component
public class CustomAuthorityUtils {
	private final List<GrantedAuthority> CLIENT_ROLES = AuthorityUtils.createAuthorityList("ROLE_CLIENT");
	private final List<GrantedAuthority> SELLER_ROLES = AuthorityUtils.createAuthorityList("ROLE_SELLER");
	private final List<String> CLIENT_ROLES_STRING = List.of("CLIENT");
	private final List<String> SELLER_ROLES_STRING = List.of("SELLER");

	public List<GrantedAuthority> createAuthorities(MemberDto.Post post) {
		if (post.getRole().equalsIgnoreCase("CLIENT")) {
			return CLIENT_ROLES;
		}
		else if (post.getRole().equalsIgnoreCase("SELLER")) {
			return SELLER_ROLES;
		}
		else throw new RuntimeException("잘못된 접근입니다.");
	}

	public List<GrantedAuthority> createAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = roles.stream()
			.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
			.collect(Collectors.toList());

		return authorities;
	}

	public List<String> createRoles(String roles) {
		if (roles.equalsIgnoreCase("CLIENT")) {
			return CLIENT_ROLES_STRING;
		}
		else if (roles.equalsIgnoreCase("SELLER")) {
			return SELLER_ROLES_STRING;
		}
		else throw new RuntimeException("잘못된 접근입니다.");
	}
}
