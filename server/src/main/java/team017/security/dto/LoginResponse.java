package team017.security.dto;

import lombok.Builder;
import lombok.Getter;

public class LoginResponse {
	@Getter
	@Builder
	public static class Member{
		private long memberId;
		private String authorization;
		private String name;
		private String role;
	}

	@Getter
	@Builder
	public static class Cilent{
		private long memberId;
		private long clientId;
		private String authorization;
		private String name;
		private String role;
	}

	@Getter
	@Builder
	public static class Seller{
		private long memberId;
		private long sellerId;
		private String authorization;
		private String name;
		private String role;
	}
}