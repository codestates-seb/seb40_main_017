package team017.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
// import team017.security.auth.AuthTokenProvider;
// import team017.security.auth.CustomOAuth2Service;
// import team017.security.auth.MemberPrincipalService;
import team017.security.provider.SecurityProvider;
import team017.security.handler.MemberAccessDeniedHandler;
import team017.security.handler.MemberAuthenticationEntryPoint;
// import team017.security.handler.OAuth2FailureHandler;
// import team017.security.handler.OAuth2SuccessHandler;
// import team017.security.refresh.RefreshTokenRepository;

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
					.antMatchers(HttpMethod.POST, "/members/signup").permitAll()
					.antMatchers(HttpMethod.POST, "/login").permitAll()
					.antMatchers(HttpMethod.POST, "/login/oauth").permitAll()
					.antMatchers(HttpMethod.GET, "/members/client/**").hasRole("CLIENT")
					.antMatchers(HttpMethod.PATCH, "members/client/**").hasRole("CLIENT")
					.antMatchers(HttpMethod.GET,"/members/seller/**").hasRole("SELLER")
					.antMatchers(HttpMethod.PATCH, "/members/seller/**").hasRole("SELLER")
					.antMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("CLIENT", "SELLER")
					.anyRequest().permitAll()
				// .anyRequest().authenticated()
			)
			// .oauth2Login()
			// .authorizationEndpoint()
			// .authorizationRequestRepository(oAuth2AuthorizationRequestRepository())
			// .and()
			// .userInfoEndpoint()
			// .userService(customOAuth2Service)
			// .and()
			// .successHandler(oAuth2AuthenticationSuccessHandler())
			// .failureHandler(oAuth2AuthenticationFailureHandler())
			// .and()
			.exceptionHandling()
			.authenticationEntryPoint(new MemberAuthenticationEntryPoint())
			.accessDeniedHandler(new MemberAccessDeniedHandler());

		// http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// private final AppProperties appProperties;
	// private final AuthTokenProvider authTokenProvider;
	// private final CustomOAuth2Service customOAuth2Service;
	// private final MemberPrincipalService memberPrincipalService;
	// private final RefreshTokenRepository refreshTokenRepository;

	// @Bean
	// public team017.security.filter.TokenAuthenticationFilter tokenAuthenticationFilter() {
	// 	return new team017.security.filter.TokenAuthenticationFilter(authTokenProvider);
	// }
	//
	// private void configure(AuthenticationManagerBuilder auth) throws Exception {
	// 	auth.userDetailsService(memberPrincipalService)
	// 		.passwordEncoder(passwordEncoder());
	// }
	//
	// @Bean
	// public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
	// 	throws Exception {
	// 	return authenticationConfiguration.getAuthenticationManager();
	// }
	//
	// @Bean
	// public HttpSessionOAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository() {
	// 	return new HttpSessionOAuth2AuthorizationRequestRepository();
	// }
	//
	// /* Oauth 인증 성공 핸들러*/
	// @Bean
	// public OAuth2SuccessHandler oAuth2AuthenticationSuccessHandler() {
	// 	return new OAuth2SuccessHandler(
	// 		authTokenProvider,
	// 		appProperties,
	// 		refreshTokenRepository,
	// 		oAuth2AuthorizationRequestRepository()
	// 	);
	// }
	//
	// /* Oauth 인증 실패 핸들러*/
	// @Bean
	// public OAuth2FailureHandler oAuth2AuthenticationFailureHandler() {
	// 	return new OAuth2FailureHandler(oAuth2AuthorizationRequestRepository());
	// }

}
