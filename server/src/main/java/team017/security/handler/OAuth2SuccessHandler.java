// package team017.security.handler;
//
// import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
// import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
// import org.springframework.stereotype.Component;
//
// import lombok.RequiredArgsConstructor;
// import team017.security.auth.AuthTokenProvider;
// import team017.security.config.AppProperties;
// import team017.security.refresh.RefreshTokenRepository;
//
// /* 소셜 로그인 성공 시 작동하는 핸들러 */
// @Component
// @RequiredArgsConstructor
// public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
// 	private final AuthTokenProvider authProvider;
// 	private final AppProperties appProperties;
// 	private final RefreshTokenRepository tokenRepository;
// 	private final HttpSessionOAuth2AuthorizationRequestRepository authRepository;
// }
