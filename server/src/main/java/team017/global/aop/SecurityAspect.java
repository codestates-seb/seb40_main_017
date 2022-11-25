package team017.global.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.security.provider.SecurityProvider;
import team017.security.refresh.RefreshToken;
import team017.security.refresh.RefreshTokenRepository;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityAspect {

	private final SecurityProvider securityProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Before("@annotation(reissueToken)")
	public void getAccessTokenAgain(ReissueToken reissueToken) throws Throwable {
		log.info("# 토큰 유효성 검사 시작");
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpServletResponse response = requestAttributes.getResponse();

		String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
		Authentication authentication = securityProvider.getAuthentication(accessToken);
		RefreshToken refreshTokenTable = refreshTokenRepository.findByKey(authentication.getName())
			.orElseThrow(()-> new RuntimeException("리프레시 토큰이 존재하지 않습니다."));
		String refreshToken = refreshTokenTable.getValue();

		if (accessToken == null) {
			throw new RuntimeException("엑세스 토큰이 존재하지 않습니다.");
		}

		/* 토큰 유효 시간 */
		Date now = new Date();
		long accessValidTime = securityProvider.getTokenClaims(accessToken).getExpiration().getTime() - now.getTime();
		long refreshValidTime = securityProvider.getTokenClaims(refreshToken).getExpiration().getTime() - now.getTime();

		/* 엑세스 토큰의 시간이 10분 이내로 남았다면 재 발급 */
		if (accessValidTime <= 1000 * 60 * 10) {
			log.info("# 엑세스 토큰 재 발급");
			log.info("# 재발급 전 엑세스 토큰 : {}", accessToken);

			String role = authentication.getAuthorities().toString().replace("[ROLE_","").replace("]", "");

			Date newAccessExpiration = new Date(now.getTime() + securityProvider.getAccessTokenTime());
			accessToken =
				securityProvider.createAccessToken(authentication.getName(), role, newAccessExpiration);
			log.info("authorities : {}", role);
			log.info("# 재발급 엑세스 토큰 : {}", accessToken);
		}

		/* 리프레시 토큰의 시간이 하루 이내로 남았다면 재 발급 */
		if (refreshValidTime <= 1000 * 60 * 60 * 24) {
			log.info("# 리프레시 토큰 재 발급");
			log.info("# 재발급 전 리프레시 토큰 : {}", refreshToken);
			Date newRefreshExpiration = new Date(now.getTime() + securityProvider.getRefreshTokenTime());
			refreshToken = securityProvider.createRefreshToken(authentication.getName(), newRefreshExpiration);

			refreshTokenTable.updateValue(refreshToken);
			refreshTokenRepository.saveAndFlush(refreshTokenTable);

			log.info("member name : {}", authentication.getName());
			log.info("# 재발급 리프레시 토큰 : {}", refreshToken);
		}

		response.setHeader("Authorization", accessToken);

		log.info("# 토큰 유효성 검사 종료 ");
	}
}
