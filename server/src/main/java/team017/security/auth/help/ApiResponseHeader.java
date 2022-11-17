package team017.security.auth.help;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponseHeader {
	private int code;
	private String message;
}
