package team017.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class LoginResponse {
	@Getter
	@Builder
	public static class Member{
		private long memberId;
		private String name;
		private String role;

		/* 소셜 로그인 시, 바로 url에서 작업할 경우 헤더에서 가져오기 힘들어서 우선 바디에 출력 중. */
		private String authorization;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Cilent{
		private long memberId;
		private long clientId;
		private String name;
		private String role;
	}

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Seller{
		private long memberId;
		private long sellerId;
		private String name;
		private String role;
	}
}