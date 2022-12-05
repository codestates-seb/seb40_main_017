package team017.global.Exception;

import lombok.Getter;

public enum ExceptionCode {

	/* 상품,판매 게시판 관련 예외 */
	BOARD_NOT_FOUND(404, "판매 게시판을 찾을 수 없습니다."),
	PRODUCT_SOLDOUT(404, "상품이 매진되었습니다."),
	PRODUCT_NOT_FOUND(404, "상품을 찾을 수 없습니다."),
	PRODUCT_NOT_ENOUGH(404, "재고가 충분하지 않습니다."),
	COMMENT_NOT_FOUND(404, "문의를 찾을 수 없습니다."),

	/* 주문 관련 예외 */
	ORDER_NOT_FOUND(404, "주문을 찾을 수 없습니다."),

	/* 리뷰 관련 예외 */
	REVIEW_NOT_FOUND(404, "리뷰가 존재하지 않습니다."),
	REVIEW_NOT_CLINET(404, "상품을 구매한 사람이 아닙니다."),

	/* Member 관련 예외 */
	MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다."),
	PASSWORD_NOT_MATCH(400, "비밀번호가 일치하지 않습니다."),
	ROLE_ERROR(400, "잘못된 역할입니다."),
	PROVIDER_ERROR(400, "로컬 사용자인지 혹은 정확한 소셜 사용자인지 확인하세요."),
	MEMBER_EXISTS(409, "이미 존재하는 이메일입니다."),
	UNAUTHORIZED_MEMBER(403, "권한이 없는 사용자 입니다."),

	/* Security Error Message */
	NOT_FOUND_AUTHORITIES(400, "권한이 없는 토큰입니다."),
	LOGIN_ERROR(400, "로그인에 실패하였습니다."),
	WRONG_ACCESS(400, "잘못된 접근입니다."),
	UNAUTHORIZED_TOKEN(400, "유효하지 않은 토큰입니다."),
	NOT_EXPIRATION_TOKEN(400, "만료되지 않은 토큰입니다."),
	LOGOUT_MEMBER(400,"로그아웃 된 사용자입니다."),
	UNAUTHORIZED(401, "유효하지 않은 인증입니다."),
	FORBIDDEN(403, "접근 권한이 없습니다."),
	NOT_FOUND_TOKEN(404, "토큰을 찾을 수 없습니다.");

	@Getter
	private int status;

	@Getter
	private String message;

	ExceptionCode(int code, String message) {
		this.status = code;
		this.message = message;
	}
}
