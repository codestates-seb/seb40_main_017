package team017.global.Exception;

import lombok.Getter;

public enum ExceptionCode {

	MEMBER_NOT_FOUND(404, "MEMBER not found"),
	PRODUCT_NOT_FOUND(404, "PRODUCT not found"),

	BOARD_NOT_FOUND(404, "BOARD not found"),

	PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
	MEMBER_EXISTS(409, "Member exists"),
	UNAUTHORIZED_MEMBER(403, "not authorized user");


	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
