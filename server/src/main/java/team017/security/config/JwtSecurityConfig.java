package team017.security.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import team017.security.jwt.filter.SecurityFilter;
import team017.security.jwt.SecurityProvider;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private final SecurityProvider securityProvider;

	@Override
	public void configure(HttpSecurity http) {
		SecurityFilter customFilter = new SecurityFilter(securityProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}