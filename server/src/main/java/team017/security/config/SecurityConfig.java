package team017.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import team017.security.jwt.service.MemberPrincipalService;
import team017.security.jwt.SecurityProvider;
import team017.security.jwt.handler.MemberAccessDeniedHandler;
import team017.security.jwt.handler.MemberAuthenticationEntryPoint;
import team017.security.oauth.handler.OAuth2FailureHandler;
import team017.security.oauth.handler.OAuth2SuccessHandler;
import team017.security.jwt.refresh.RefreshTokenRepository;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final SecurityProvider securityProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.headers().frameOptions().sameOrigin()
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.apply(new JwtSecurityConfig(securityProvider))
			.and()
			.authorizeHttpRequests(authorize -> authorize
					.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

					/* 회원 관련 접근 제한 */
					.antMatchers(HttpMethod.POST, "/members/signup").permitAll()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/login/oauth").permitAll()
					.antMatchers(HttpMethod.GET, "/members/client/**").hasRole("CLIENT")
					.antMatchers(HttpMethod.PATCH, "members/client/**").hasRole("CLIENT")
					.antMatchers(HttpMethod.GET,"/members/seller/**").permitAll()
					.antMatchers(HttpMethod.PATCH, "/members/seller/**").hasRole("SELLER")
					.antMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("CLIENT", "SELLER")

					/* 소셜 수정 권한 접근 */
					// .antMatchers(HttpMethod.PATCH, "/social/**").hasRole("SOCIAL")

					/* 판매 게시판 관련 접근 제한 */
					// .antMatchers(HttpMethod.GET, "/boards").permitAll()
					// .antMatchers(HttpMethod.GET, "/boards/**").permitAll()
					// .antMatchers(HttpMethod.POST, "/boards").hasRole("SELLER")
					// .antMatchers(HttpMethod.PATCH, "/boards/*").hasRole("SELLER")
					// .antMatchers(HttpMethod.DELETE, "/boards/*").hasRole("SELLER")

					/* 리뷰 관련 접근 제한 */
					// .antMatchers(HttpMethod.GET, "/boards/reviews/**").permitAll()
					// .antMatchers(HttpMethod.POST, "/boards/*/reviews").hasRole("CLIENT")
					// .antMatchers(HttpMethod.PATCH, "/boars/reviews/**").hasRole("CLIENT")
					// .antMatchers(HttpMethod.DELETE, "/boards/reviews/**").hasRole("CLIENT")

					/* 주문 관련 접근 제한 */
					// .antMatchers(HttpMethod.POST, "/orders").hasRole("CLIENT")

					/* 문의 관련 접근 제한 */
					// .antMatchers(HttpMethod.GET, "/comments/*").permitAll()
					// .antMatchers(HttpMethod.POST, "/comments").hasAnyRole("SELLER", "CLIENT")
					// .antMatchers(HttpMethod.PATCH, "/comments/**").hasAnyRole("SELLER", "CLIENT")
					// .antMatchers(HttpMethod.DELETE, "/comments/**").hasAnyRole("SELLER", "CLIENT")

					/* 그 외 접근 허용 */
					.anyRequest().permitAll()
			)
			// .oauth2Login(oauth -> oauth
			// 	.successHandler(oAuth2AuthenticationSuccessHandler())
			// 	.failureHandler(oAuth2AuthenticationFailureHandler())
			// )
			.exceptionHandling()
			.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
			.accessDeniedHandler(new MemberAccessDeniedHandler());

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	private final MemberPrincipalService memberPrincipalService;
	private final RefreshTokenRepository refreshTokenRepository;

	private void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(memberPrincipalService)
			.passwordEncoder(passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
		throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public HttpSessionOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository() {
		return new HttpSessionOAuth2AuthorizationRequestRepository();
	}

	/* Oauth 인증 성공 핸들러*/
	@Bean
	public OAuth2SuccessHandler oAuth2AuthenticationSuccessHandler() {
		return new OAuth2SuccessHandler(
			refreshTokenRepository,
			oAuth2AuthorizationRequestRepository(),
			securityProvider
		);
	}

	/* Oauth 인증 실패 핸들러*/
	@Bean
	public OAuth2FailureHandler oAuth2AuthenticationFailureHandler() {
		return new OAuth2FailureHandler(oAuth2AuthorizationRequestRepository());
	}
}
