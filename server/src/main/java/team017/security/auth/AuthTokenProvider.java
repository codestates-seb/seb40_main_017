// package team017.security.auth;
//
// import java.security.Key;
// import java.util.Arrays;
// import java.util.Collection;
// import java.util.Date;
// import java.util.stream.Collectors;
//
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.stereotype.Component;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.security.Keys;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// // @Component
// public class AuthTokenProvider {
//
// 	private final Key key;
//
// 	/* Secret Key 생성 -> Jwt Config */
// 	public AuthTokenProvider(String secret) {
// 		this.key = Keys.hmacShaKeyFor(secret.getBytes());
// 	}
//
// 	public AuthToken createAuthToken(String id, Date expiry) {
// 		return new AuthToken(id, expiry, key);
// 	}
//
// 	public AuthToken createAuthToken(String id, String role, Date expiry) {
// 		return new AuthToken(id, role, expiry, key);
// 	}
//
// 	public AuthToken convertAuthToken(String token) {
// 		return new AuthToken(token, key);
// 	}
//
// 	public Authentication getAuthentication(AuthToken authToken) {
//
// 		/* 토큰이 존재하면 조건문 실행 */
// 		if(authToken.validate()) {
//
// 			/* Claim 에서 권한 가져오기 */
// 			Claims claims = authToken.getTokenClaims();
// 			Collection<? extends GrantedAuthority> authorities =
// 				Arrays.stream(new String[]{claims.get("role").toString()})
// 					.map(SimpleGrantedAuthority::new)
// 					.collect(Collectors.toList());
//
// 			log.debug("claims subject := [{}]", claims.getSubject());
//
// 			/* Claim 에서 권한 잘 가져오는지 로그 확인 */
// 			log.info("## 권한 : {}", authorities);
// 			User principal = new User(claims.getSubject(), "", authorities);
//
// 			return new UsernamePasswordAuthenticationToken(principal, authToken, authorities);
// 		} else {
// 			throw new RuntimeException("유효하지 않은 토큰입니다.");
// 		}
// 	}
// }
//
