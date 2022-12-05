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
import team017.security.jwt.refresh.RefreshTokenRepository;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
	private final SecurityProvider securityProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// .headers().frameOptions().sameOrigin()
			// .and()
			.csrf().disable()
			.cors()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.formLogin().disable()
			.httpBasic().disable()
			.apply(new JwtSecurityConfig(securityProvider))
			.and()
			.authorizeHttpRequests(authorize -> authorize

				/* 메인 페이지는 모두 접근이 가능해야한다. */
				.antMatchers(HttpMethod.GET, "/").permitAll() /* 메인 페이지 */

				/* 회원 관련 접근 제한 */
				.antMatchers(HttpMethod.POST, "/members/signup").permitAll() /* 자체 회원가입 */
				.antMatchers(HttpMethod.POST, "/login").permitAll() /* 자체 로그인 */
				.antMatchers(HttpMethod.GET, "/login/**").permitAll() /* 소셜 로그인을 위해 */
				.antMatchers(HttpMethod.POST, "/login/**").permitAll() /* 소셜 로그인을 위해 */
				.antMatchers(HttpMethod.GET, "/members/client/**").hasRole("CLIENT") /* 소비자 정보 조회 */
				.antMatchers(HttpMethod.PUT, "/members/client/**").hasRole("CLIENT") /* 소비자 정보 수정 */
				.antMatchers(HttpMethod.GET,"/members/seller/**").permitAll() /* 생산자 정보 조회 -> 모두가 조회 가능 */
				.antMatchers(HttpMethod.PUT, "/members/seller/**").hasRole("SELLER") /* 생산자 정보 수정 */
				.antMatchers(HttpMethod.DELETE, "/members/**").hasAnyRole("CLIENT", "SELLER") /* 회원 탈퇴 */
				.antMatchers(HttpMethod.GET, "/access").permitAll() /* 새로고침 */
				.antMatchers(HttpMethod.GET, "/mypage/sold/*").permitAll() /* 생산자 게시판 조회 */
				.antMatchers(HttpMethod.GET, "/mypage/*").hasRole("CLIENT") /* 구매자 주문내역 조회 */

				/* 소셜 수정 권한 접근 */
				.antMatchers(HttpMethod.PUT, "/social/**").hasRole("SOCIAL") /* 만약 소셜 권한을 부여할 경우 */

				/* 판매 게시판 관련 접근 제한 */
				.antMatchers(HttpMethod.GET, "/boards").permitAll() /* 판매 게시판 조회 */
				.antMatchers(HttpMethod.GET, "/boards/**").permitAll() /* 판매 게시판 세부 조회 */
				.antMatchers(HttpMethod.POST, "/boards").hasRole("SELLER") /* 판매 게시판 등록 */
				.antMatchers(HttpMethod.PATCH, "/boards/*").hasRole("SELLER") /* 판매 게시판 수정 */
				.antMatchers(HttpMethod.DELETE, "/boards/*").hasRole("SELLER") /* 판매 게시판 삭제*/

				/* 리뷰 관련 접근 제한 */
				// .antMatchers(HttpMethod.GET, "/boards/reviews/**").permitAll() /* 리뷰 조회 -> 판매 게시판 세부 조회 덕분에 없어도 됨. */
				.antMatchers(HttpMethod.POST, "/boards/*/reviews").hasRole("CLIENT") /* 리뷰 등록 */
				// .antMatchers(HttpMethod.PATCH, "/boars/reviews/**").hasRole("CLIENT") /* 리뷰 수정 */
				.antMatchers(HttpMethod.DELETE, "/boards/reviews/**").hasRole("CLIENT") /* 리뷰 삭제 */

				/* 주문 관련 접근 제한 */
				.antMatchers(HttpMethod.POST, "/orders").hasRole("CLIENT") /* 주문 등록 */

				/* 결제 관련 접근 제한 */
				.antMatchers(HttpMethod.GET, "/order/pay/completed").permitAll() /* 결제 승인 요청 */
				.antMatchers(HttpMethod.GET, "/order/pay/cancel").permitAll() /* 결제 취소 */
				.antMatchers(HttpMethod.GET, "/order/pay/fail").permitAll() /* 결제 실패 */
				.antMatchers(HttpMethod.DELETE, "/orders/**").permitAll() /* 주문 삭제 -> 결제 취소 혹은 실패시 작동 */
				.antMatchers(HttpMethod.GET, "/orders/*").permitAll() /* 결제 성공 -> 주문 내역 조회 필요 */
				.antMatchers(HttpMethod.GET, "/order/pay/*").hasRole("CLIENT") /* 결제 요청 */

				/* 문의 관련 접근 제한 */
				.antMatchers(HttpMethod.GET, "/comments/*").permitAll() /* 문의 조회 */
				.antMatchers(HttpMethod.POST, "/comments").hasAnyRole("SELLER", "CLIENT") /* 문의 등록 */
				.antMatchers(HttpMethod.PATCH, "/comments/**").hasAnyRole("SELLER", "CLIENT") /* 문의 수정 */
				.antMatchers(HttpMethod.DELETE, "/comments/**").hasAnyRole("SELLER", "CLIENT") /* 문의 삭제 */

				/* 혹시 모르는 options 발생 시 허용 */
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				/* 접근 제한 확인을 위해 나머지는 접근할 수 없는 권한으로 설정 */
				.anyRequest().permitAll()
			)
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
}
