// package team017.security.auth;
//
// import java.util.Date;
//
// import javax.servlet.http.Cookie;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import io.jsonwebtoken.Claims;
// import lombok.RequiredArgsConstructor;
// import team017.security.auth.help.ApiResponse;
// import team017.security.auth.help.AuthReqModel;
// import team017.security.config.AppProperties;
// import team017.security.refresh.RefreshToken;
// import team017.security.refresh.RefreshTokenRepository;
// import team017.security.utils.CookieUtil;
//
// @RestController
// @RequestMapping
// @RequiredArgsConstructor
// public class AuthController {
// 	private final AppProperties appProperties;
// 	private final AuthTokenProvider authTokenProvider;
// 	private final AuthenticationManager authenticationManager;
// 	private final RefreshTokenRepository refreshTokenRepository;
//
// 	private final static long THREE_DAYS_MSEC = 259200000;
// 	private final static String REFRESH_TOKEN = "refresh_token";
//
// 	@PostMapping("/login/oauth")
// 	public ApiResponse login(HttpServletRequest request, HttpServletResponse response,
// 			@RequestBody AuthReqModel authReqModel) {
// 		Authentication authentication =
// 			authenticationManager.authenticate(
// 				new UsernamePasswordAuthenticationToken(authReqModel.getUsername(), authReqModel.getPassword())
// 			);
// 		String username = authReqModel.getUsername();
// 		SecurityContextHolder.getContext().setAuthentication(authentication);
//
// 		Date now = new Date();
// 		AuthToken accessToken = authTokenProvider.createAuthToken(
// 			username,
// 			((MemberPrincipal) authentication.getPrincipal()).getRole(),
// 			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
// 		);
//
// 		long refreshTokenExpiry = appProperties.getAuth().getRefreshExpiry();
// 		AuthToken refreshToken = authTokenProvider.createAuthToken(
// 			appProperties.getAuth().getTokenSecret(),
// 			new Date(now.getTime() + refreshTokenExpiry)
// 		);
//
// 		// userId refresh token 으로 DB 확인
// 		RefreshToken userRefreshToken = refreshTokenRepository.findById(username);
// 		if (userRefreshToken == null) {
// 			// 없는 경우 새로 등록
// 			userRefreshToken = new RefreshToken(username, refreshToken.getToken());
// 			refreshTokenRepository.saveAndFlush(userRefreshToken);
// 		} else {
// 			// DB에 refresh 토큰 업데이트
// 			userRefreshToken.setValue(refreshToken.getToken());
// 		}
//
// 		int cookieMaxAge = (int) refreshTokenExpiry / 60;
// 		CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
// 		CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken.getToken(), cookieMaxAge);
//
// 		return ApiResponse.success("token", accessToken.getToken());
// 	}
//
// 	@GetMapping("/oauth/refresh")
// 	public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
// 		// access token 확인
// 		String accessToken = request.getHeader("Authorization");
// 		AuthToken authToken = authTokenProvider.convertAuthToken(accessToken);
// 		if (!authToken.validate()) {
// 			return ApiResponse.invalidAccessToken();
// 		}
//
// 		Claims claims = authToken.getExpiredTokenClaims();
// 		if (claims == null) {
// 			return ApiResponse.notExpiredTokenYet();
// 		}
//
// 		String username = claims.getSubject();
// 		String role = claims.get("role", String.class);
//
// 		// refresh token
// 		String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
// 			.map(Cookie::getValue)
// 			.orElse((null));
// 		AuthToken authRefreshToken = authTokenProvider.convertAuthToken(refreshToken);
//
// 		if (authRefreshToken.validate()) {
// 			return ApiResponse.invalidRefreshToken();
// 		}
//
// 		// userId refresh token 으로 DB 확인
// 		RefreshToken userRefreshToken = refreshTokenRepository.findByIdAndValue(username, refreshToken);
// 		if (userRefreshToken == null) {
// 			return ApiResponse.invalidRefreshToken();
// 		}
//
// 		Date now = new Date();
// 		AuthToken newAccessToken = authTokenProvider.createAuthToken(
// 			username,
// 			role,
// 			new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
// 		);
//
// 		long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
//
// 		/* refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신 */
// 		if (validTime <= THREE_DAYS_MSEC) {
//
// 			/* refresh 토큰 설정 */
// 			long refreshTokenExpiry = appProperties.getAuth().getRefreshExpiry();
//
// 			authRefreshToken = authTokenProvider.createAuthToken(
// 				appProperties.getAuth().getTokenSecret(),
// 				new Date(now.getTime() + refreshTokenExpiry)
// 			);
//
// 			/* DB에 refresh 업데이트 */
// 			userRefreshToken.updateValue(authRefreshToken.getToken());
//
// 			int cookieMaxAge = (int) refreshTokenExpiry / 60;
// 			CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
// 			CookieUtil.addCookie(response, REFRESH_TOKEN, authRefreshToken.getToken(), cookieMaxAge);
// 		}
//
// 		return ApiResponse.success("token", newAccessToken.getToken());
// 	}
// }
