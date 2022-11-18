package team017.security.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
	private long memberId;
	private String authorization;
	private String name;
	private String role;
}
