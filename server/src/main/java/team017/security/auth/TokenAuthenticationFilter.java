// package team017.security.filter;
//
// import java.io.IOException;
//
// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import team017.security.auth.AuthToken;
// import team017.security.auth.AuthTokenProvider;
//
// @Slf4j
// @RequiredArgsConstructor
// public class TokenAuthenticationFilter extends OncePerRequestFilter {
// 	private final AuthTokenProvider tokenProvider;
//
// 	@Override
// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
// 		FilterChain filterChain)  throws ServletException, IOException {
//
// 		String tokenStr = request.getHeader("Authorization");
// 		AuthToken token = tokenProvider.convertAuthToken(tokenStr);
//
// 		if (token.validate()) {
// 			Authentication authentication = tokenProvider.getAuthentication(token);
// 			SecurityContextHolder.getContext().setAuthentication(authentication);
// 		}
//
// 		filterChain.doFilter(request, response);
// 	}
// }
//
