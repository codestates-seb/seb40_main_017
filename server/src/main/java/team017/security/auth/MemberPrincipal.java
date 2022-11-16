// package team017.security.auth;
//
// import java.util.Collection;
// import java.util.Collections;
// import java.util.Map;
//
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.oauth2.core.oidc.OidcIdToken;
// import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.security.oauth2.core.user.OAuth2User;
//
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.Setter;
// import team017.member.entity.Member;
// import team017.member.entity.ProviderType;
//
// @Getter
// @Setter
// @AllArgsConstructor
// @RequiredArgsConstructor
// public class MemberPrincipal implements UserDetails, OAuth2User, OidcUser {
//
// 	/* username == email */
// 	private final String username;
// 	private final String password;
// 	private final ProviderType providerType;
// 	private final String role;
// 	private final Collection<GrantedAuthority> authorities;
// 	private Map<String, Object> attributes;
//
// 	public static MemberPrincipal create(Member member) {
// 		return new MemberPrincipal(
// 			member.getEmail(),
// 			member.getPassword(),
// 			member.getProviderType(),
// 			member.getRole(),
// 			Collections.singletonList(new SimpleGrantedAuthority(member.getRoles().toString()))
// 		);
// 	}
//
// 	public static MemberPrincipal create(Member member, Map<String, Object> attributes) {
// 		MemberPrincipal memberPrincipal = create(member);
// 		memberPrincipal.setAttributes(attributes);
//
// 		return memberPrincipal;
// 	}
// 	@Override
// 	public String getName() {
// 		return username;
// 	}
//
// 	@Override
// 	public Map<String, Object> getAttributes() {
// 		return attributes;
// 	}
//
// 	@Override
// 	public Collection<? extends GrantedAuthority> getAuthorities() {
// 		return authorities;
// 	}
//
// 	@Override
// 	public String getPassword() {
// 		return password;
// 	}
//
// 	@Override
// 	public String getUsername() {
// 		return username;
// 	}
//
// 	@Override
// 	public boolean isAccountNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isAccountNonLocked() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isCredentialsNonExpired() {
// 		return true;
// 	}
//
// 	@Override
// 	public boolean isEnabled() {
// 		return true;
// 	}
//
// 	@Override
// 	public Map<String, Object> getClaims() {
// 		return null;
// 	}
//
// 	@Override
// 	public OidcUserInfo getUserInfo() {
// 		return null;
// 	}
//
// 	@Override
// 	public OidcIdToken getIdToken() {
// 		return null;
// 	}
// }
