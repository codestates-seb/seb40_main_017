// package team017.security.auth;
//
// import java.security.Key;
// import java.util.Date;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.UnsupportedJwtException;
//
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @RequiredArgsConstructor
// public class AuthToken {
//
// 	@Getter
// 	private final String token;
// 	private final Key key;
//
// 	private static final String AUTHORITIES_KEY = "role";
//
// 	AuthToken(String id, Date expiration, Key key) {
// 		this.key = key;
// 		this.token = createRefreshToken(id, expiration);
// 	}
//
// 	AuthToken(String id, String role, Date expiration, Key key) {
// 		this.key = key;
// 		this.token = createAccessToken(id, role, expiration);
// 	}
//
//
// 	/* 리프레시 토큰 발급? -> refresh token 은 claims 가 필요 없으니까, 리프레시 토큰이지 않을까 */
// 	private String createRefreshToken(String id, Date expiration) {
// 		return Jwts.builder()
// 			.setSubject(id)
// 			.signWith(key, SignatureAlgorithm.HS256)
// 			.setExpiration(expiration)
// 			.compact();
// 	}
//
// 	/* 엑세스 토큰 발급? -> access token 은 claims 가 필요하니까, 엑세스 토큰이지 않을까 */
// 	private String createAccessToken(String id, String role, Date expiration) {
// 		return Jwts.builder()
// 			.setSubject(id)
// 			.claim(AUTHORITIES_KEY, role)
// 			.signWith(key, SignatureAlgorithm.HS256)
// 			.setExpiration(expiration)
// 			.compact();
// 	}
//
// 	/* 토큰 존재 여부 판별 */
// 	public boolean validate() {
// 		return this.getTokenClaims() != null;
// 	}
//
// 	public Claims getTokenClaims() {
// 		try {
// 			return Jwts.parserBuilder()
// 				.setSigningKey(key)
// 				.build()
// 				.parseClaimsJws(token)
// 				.getBody();
// 		} catch (SecurityException exception) {
// 			log.info("유효하지 않은 JWT 토큰 서명입니다.");
// 		} catch (MalformedJwtException exception) {
// 			log.info("유효하지 않은 JWT 토큰입니다.");
// 		} catch (ExpiredJwtException exception) {
// 			log.info("만료된 JWT 토큰입니다.");
// 		} catch (UnsupportedJwtException exception) {
// 			log.info("지원되지 않는 JWT 토큰입니다.");
// 		} catch (IllegalArgumentException exception) {
// 			log.info("토큰이 잘못되었습니다.");
// 		}
// 		return null;
// 	}
//
// 	public Claims getExpiredTokenClaims() {
// 		try {
// 			Jwts.parserBuilder()
// 				.setSigningKey(key)
// 				.build()
// 				.parseClaimsJws(token)
// 				.getBody();
// 		} catch (ExpiredJwtException exception) {
// 			log.info("만료된 JWT 토큰입니다.");
// 			return exception.getClaims();
// 		}
// 		return null;
// 	}
// }
//
