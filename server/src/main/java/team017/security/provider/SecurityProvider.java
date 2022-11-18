package team017.security.provider;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.security.dto.TokenDto;
import team017.security.utils.CustomAuthorityUtils;

@Slf4j
@Component
public class SecurityProvider{
	private final Key key;
	private final CustomAuthorityUtils authorityUtils;

	private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
	private static final String AUTHORITIES_KEY = "role";
	private static final String BEARER_TYPE = "Bearer ";

	/* 시크릿 키 */
	public SecurityProvider(@Value("${jwt.secret-key}") String secretKey, CustomAuthorityUtils authorityUtils) {
		this.authorityUtils = authorityUtils;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public TokenDto generatedTokenDto(Authentication authentication) {

		/* 권한 가져오기 */
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = (new Date()).getTime();
		Date accessTokenExpiration = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
		Date refreshTokenExpiration = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

		/* 1️⃣ Access Token 생성 */
		String accessToken = createAccessToken(authentication.getName(), authorities, accessTokenExpiration);

		/* 2️⃣ Refresh Token 생성 */
		String refreshToken = createRefreshToken(authentication.getName(), refreshTokenExpiration);

		return TokenDto.builder()
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.accessTokenExpiresIn(accessTokenExpiration.getTime())
			.refreshToken(refreshToken)
			.build();
	}

	/* 1️⃣ Access Token 생성 */
	public String createAccessToken(String username, String role, Date expiration) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);
		claims.put("role", role);

		String accessToken =
			Jwts.builder()
			.setSubject(username)
				.setClaims(claims)
				.signWith(key, SignatureAlgorithm.HS256)
				.setExpiration(expiration)
				.compact();

		return BEARER_TYPE + accessToken;
	}

	/* 2️⃣ Refresh Token 생성 */
	public String createRefreshToken(String username, Date expiration) {
		return Jwts.builder()
			.setSubject(username)
			.signWith(key, SignatureAlgorithm.HS256)
			.setExpiration(expiration)
			.compact();
	}


	public Authentication getAuthentication(String accessToken) {

		/* 1. 토큰 복호화 */
		Map<String, Object> claims = parseClaims(accessToken);

		/* 만약 복호화 한 토큰 안에 권한이 없으면 예외 던지기 */
		if (claims.get(AUTHORITIES_KEY) == null) {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_AUTHORITIES);
		}

		/* 권한이 있다면, 권한 가져오기 */
		List<GrantedAuthority> authorities = authorityUtils.createAuthorities((String)claims.get(AUTHORITIES_KEY));

		/* UserDetails 객체를 만들어 Authentication 리턴 */
		UserDetails principal = new User((String)claims.get("username"), "", authorities);

		/* 2. 권한이 잘 리턴 되는지 확인 */
		log.info("2. 리턴 된 권한 : " + principal.getAuthorities());

		return new UsernamePasswordAuthenticationToken(principal, null, authorities);
	}

	/* 토큰 존재 여부 판별 */
	public boolean validate(String token) {
		return this.getTokenClaims(token) != null;
	}

	public Claims getTokenClaims(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (SecurityException exception) {
			log.info("유효하지 않은 JWT 토큰 서명입니다.");
		} catch (MalformedJwtException exception) {
			log.info("유효하지 않은 JWT 토큰입니다.");
		} catch (ExpiredJwtException exception) {
			log.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException exception) {
			log.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException exception) {
			log.info("무언가 잘못되었습니다.");
		}
		return null;
	}

	/* 토큰 복호화 */
	public Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key).build()
				.parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException exception) {
			log.info("이미 만료된 토큰입니다.");

			return exception.getClaims();
		}
	}

	public long getAccessTokenTime() {
		return ACCESS_TOKEN_EXPIRE_TIME;
	}
	public long getRefreshTokenTime() {
		return REFRESH_TOKEN_EXPIRE_TIME;
	}
}
