package team017.global.Exception;

import lombok.Getter;

public enum ExceptionCode {

	MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
	PRODUCT_NOT_FOUND(404, "PRODUCT not found"),

	BOARD_NOT_FOUND(404, "BOARD not found"),

	PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
	UNAUTHORIZED_MEMBER(403, "not authorized user"),
	COMMENT_NOT_FOUND(404, "Comment not found"),

	REVIEW_NOT_FOUND(404, "리뷰가 존재하지 않습니다."),

	/* Security Error Message */
	UNAUTHORIZED(401, "유효하지 않은 인증입니다."),
	LOGIN_ERROR(401, "로그인에 실패하였습니다."),
	NOT_FOUND_AUTHORITIES(404, "권한이 없는 토큰입니다."),
	FORBIDDEN(403, "접근 권한이 없습니다."),
	MEMBER_EXISTS(409, "이미 존재하는 이메일입니다.");

	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
