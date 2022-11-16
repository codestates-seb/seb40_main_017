// package team017.security.auth;
//
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// import team017.security.auth.AuthTokenProvider;
//
// // @Configuration
// public class JwtConfig {
// 	@Value("${jwt.secret-key}")
// 	private String secretKey;
//
// 	@Bean
// 	public AuthTokenProvider jwtProvider() {
// 		return new AuthTokenProvider(secretKey);
// 	}
// }
//
