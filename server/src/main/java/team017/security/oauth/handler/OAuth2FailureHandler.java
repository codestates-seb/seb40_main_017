package team017.security.oauth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import team017.security.utils.CookieUtil;

/* 소셜 인증 실패 시 작동하는 핸들러 */
@RequiredArgsConstructor
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {
	private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
	private final HttpSessionOAuth2AuthorizationRequestRepository httpRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		String targetUrl = CookieUtil.getCookie(request,REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue)
			.orElse(("/"));
		exception.printStackTrace();

		targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("error", exception.getLocalizedMessage())
			.build().toUriString();

		httpRepository.removeAuthorizationRequest(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
