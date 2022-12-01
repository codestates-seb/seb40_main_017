package team017.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import team017.security.jwt.SecurityProvider;

@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	private final SecurityProvider securityProvider;
	private static final String BEARER_TYPE = "Bearer ";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		/* Header 에서 토큰 꺼내기 ->BEARER_TYPE 이 지워진 채 리턴*/
		String token = resolvedToken(request);

		/* 토큰이 공백 제외 1글자 이상이고, 유효성 검사를 통과하면 조건문 실행 */
		if (StringUtils.hasText(token) && securityProvider.validate(token)) {
			Authentication authentication = securityProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		/* 필터 체인 전달 요청 */
		filterChain.doFilter(request, response);
	}

	private String resolvedToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
			return bearerToken.substring(BEARER_TYPE.length());
		}

		return null;
	}
}
