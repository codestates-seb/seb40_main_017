package team017.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* CORS 설정을 위한 configuration */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedMethods("POST", "PATCH", "GET", "DELETE", "OPTIONS") /* 요청 가능한 메서드 */
			.allowedHeaders("*") /* 헤더 허용 */
			.exposedHeaders("Authorization", "Refresh") /* 헤더를 통하여 토큰을 전달해야 하기 때문, 추가 헤더 허용 */
			.allowedOriginPatterns("") /* 배포 시에는 URL 이 달라지므로 와일드 카드 사용 */
			 .maxAge(3600) /* pre flight 요청에 대한 응답을 캐싱하는 시간 */
			.allowCredentials(true); /* 쿠키 요청 허용 */
	}
}