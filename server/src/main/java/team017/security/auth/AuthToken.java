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
// import org.springframework.security.core.userdetails.UserDetails;
//
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.ExpiredJwtException;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.UnsupportedJwtException;
//
// import io.jsonwebtoken.security.Keys;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import team017.security.jwt.dto.TokenDto;
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
// 	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
// 	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
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
// 			log.info("무언가 잘못되었습니다.");
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
// 	// public TokenDto generatedTokenDto(Authentication authentication) {
// 	//
// 	// 	/* 권한 가져오기 */
// 	// 	String authorities = authentication.getAuthorities().stream()
// 	// 		.map(GrantedAuthority::getAuthority)
// 	// 		.collect(Collectors.joining(","));
// 	// 	log.info("### 권한 : {}", authorities);
// 	// 	long now = (new Date()).getTime();
// 	//
// 	// 	/* Access Token 생성 */
// 	// 	Date accessTokenExpiration = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
// 	// 	String accessToken = Jwts.builder()
// 	// 		.setSubject(authentication.getName())
// 	// 		.claim(AUTHORITIES_KEY, authorities)
// 	// 		.setExpiration(accessTokenExpiration)
// 	// 		.signWith(key, SignatureAlgorithm.HS512)
// 	// 		.compact();
// 	// 	accessToken = "Bearer" + accessToken;
// 	//
// 	// 	/* Refresh Token 생성 */
// 	// 	String refreshToken = Jwts.builder()
// 	// 		.setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
// 	// 		.signWith(key, SignatureAlgorithm.HS512)
// 	// 		.compact();
// 	//
// 	// 	return TokenDto.builder()
// 	// 		.grantType("Bearer")
// 	// 		.accessToken(accessToken)
// 	// 		.accessTokenExpiresIn(accessTokenExpiration.getTime())
// 	// 		.refreshToken(refreshToken)
// 	// 		.build();
// 	// }
// 	// /* Token 판별 */
// 	// public boolean validateToken (String token) {
// 	// 	try {
// 	// 		Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
// 	// 		return true;
// 	// 	} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException exception) {
// 	// 		log.info("잘못된 JWT 서명입니다.");
// 	// 	} catch (ExpiredJwtException exception) {
// 	// 		log.info("만료된 토큰입니다.");
// 	// 	} catch (UnsupportedJwtException exception) {
// 	// 		log.info("지원되지 않는 토큰입니다.");
// 	// 	} catch (IllegalArgumentException exception) {
// 	// 		log.info("토큰이 잘못되었습니다.");
// 	// 	}
// 	// 	return false;
// 	// }
// 	//
// 	// private Claims parseClaims(String accessToken) {
// 	// 	try {
// 	// 		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
// 	// 	} catch (ExpiredJwtException exception) {
// 	// 		return exception.getClaims();
// 	// 	}
// 	// }
// 	// public Authentication getAuthentication(String accessToken) {
// 	//
// 	// 	/* 토큰 복호화 */
// 	// 	Claims claims = parseClaims(accessToken);
// 	//
// 	// 	if (claims.get(AUTHORITIES_KEY) == null) {
// 	// 		throw new RuntimeException("권한이 없습니다.");
// 	// 	}
// 	//
// 	// 	/* claim 에서 권한 가져오기 */
// 	// 	Collection<? extends GrantedAuthority> authorities;
// 	// 	authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
// 	// 		.map(SimpleGrantedAuthority::new)
// 	// 		.collect(Collectors.toList());
// 	//
// 	// 	/* UserDetails 객체를 만들어 Authentication 리턴 */
// 	// 	UserDetails principal = new User(claims.getSubject(), "", authorities);
// 	// 	log.info("#### 권한 : {}", principal.getAuthorities().toString());
// 	// 		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
// 	// }
// }
//
