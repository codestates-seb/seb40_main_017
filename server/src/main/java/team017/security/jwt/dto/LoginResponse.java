package team017.security.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
	private long memberId;
	private String authorization;
	private String refresh;
}
