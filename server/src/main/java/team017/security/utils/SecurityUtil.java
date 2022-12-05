package team017.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtil {
	private SecurityUtil() {}

	public static String getCurrentEmail() {

		/* ContextHolder 에서 정보 가져오기 */
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getName() == null) {
			throw new RuntimeException("해당하는 인증 정보가 존재하지 않습니다.");
		}

		return authentication.getName();
	}
}
