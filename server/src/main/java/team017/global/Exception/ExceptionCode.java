package team017.global.Exception;

import lombok.Getter;

public enum ExceptionCode {

	MEMBER_NOT_FOUND(404, "MEMBER not found"),
	PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
	MEMBER_EXISTS(409, "Member exists");

	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}