package team017.security.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
// import team017.security.auth.AuthToken;
// import team017.security.auth.AuthTokenProvider;
import team017.security.jwt.JwtFilter;
import team017.security.jwt.JwtProvider;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private final JwtProvider jwtProvider;
	// private final AuthToken authToken;

	@Override
	public void configure(HttpSecurity http) {
		// JwtFilter customFilter = new JwtFilter(authToken);
		JwtFilter customFilter = new JwtFilter(jwtProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}