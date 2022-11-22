package team017.security.handler;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import team017.member.entity.ProviderType;
import team017.security.info.OAuth2UserInfo;
import team017.security.info.OAuth2UserInfoFactory;
import team017.security.dto.TokenDto;
import team017.security.provider.SecurityProvider;
import team017.security.refresh.RefreshToken;
import team017.security.refresh.RefreshTokenRepository;
import team017.security.utils.CookieUtil;

/* 소셜 로그인 성공 시 작동하는 핸들러 */
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final RefreshTokenRepository refreshTokenRepository;
	private final HttpSessionOAuth2AuthorizationRequestRepository authRepository;
	private final SecurityProvider securityProvider;
	private static String REDIRECT_URL = "http://localhost:3000/oauth/redirect";

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		var oAuth2User = (OAuth2User) authentication.getPrincipal();
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Optional<String> redirectUri = CookieUtil.getCookie(request, "")
			.map(Cookie::getValue);

		if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new IllegalArgumentException("죄송합니다. 승인되지 않은 리디렉션이 존재합니다.");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
		ProviderType providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());

		OidcUser user = ((OidcUser) authentication.getPrincipal());
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
		Collection<? extends GrantedAuthority> authorities = ((OidcUser) authentication.getPrincipal()).getAuthorities();

		String role = "SOCIAL";
		TokenDto tokenDto = securityProvider.generatedTokenDto(authentication.getName());

		/* DB 저장 */
		RefreshToken userRefreshToken = refreshTokenRepository.findRefreshTokenByKey(userInfo.getEmail());
		if (userRefreshToken != null) {
			userRefreshToken.setValue(tokenDto.getRefreshToken());
		} else {
			userRefreshToken = new RefreshToken(userInfo.getEmail(), tokenDto.getRefreshToken());
			refreshTokenRepository.saveAndFlush(userRefreshToken);
		}

		int cookieMaxAge = (int) 1000 * 60 * 24 * 7 ;

		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
		CookieUtil.addCookie(response, REFRESH_TOKEN, tokenDto.getRefreshToken(), cookieMaxAge);

		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("token", tokenDto.getAccessToken())
			.build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		authRepository.removeAuthorizationRequest(request, response);
	}

	private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
		if (authorities == null) {
			return false;
		}

		for (GrantedAuthority grantedAuthority : authorities) {
			if (authority.equals(grantedAuthority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);
		URI authorizedURI = URI.create(REDIRECT_URL);
		if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
			&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
			return true;
		}
		return false;
	}
}
