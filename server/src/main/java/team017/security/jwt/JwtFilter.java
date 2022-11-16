package team017.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import team017.security.jwt.JwtProvider;
import team017.security.utils.CustomAuthorityUtils;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String authorization = request.getHeader("Authorization");

		return authorization == null || !authorization.startsWith("Bearer");
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		 /* Header 에서 토큰 꺼내기 -> 토큰에서 Bearer 가 지워진 채 리턴 */
		String jwt = resolveToken(request);

		/* 해당 토큰이 문자열을 가지고 있고(공백 제외 1글자 이상) && TokenProvider 에서 유효성 검사가 통과하면 SecurityContextHolder 에 저장 */
		if (StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)) {
			Authentication authentication = jwtProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		/* 필터 체인 전달 요청 */
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		/* Header 에서 Authorization 값 가져오기 */
		String bearerToken = request.getHeader("Authorization");

		/* bearToken 이 문자열을 가지고 있고, Bearer 로 시작하면 조건문 실행 */
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {

			/* Bearer(6글자, 인덱스 5번까지)를 지우고 그 다음 6번째 인덱스부터 리턴 */
			return bearerToken.substring("Bearer".length());
		}
		return null;
	}
}
