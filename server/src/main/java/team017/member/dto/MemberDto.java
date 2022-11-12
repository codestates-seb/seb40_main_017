package team017.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDto {

	@Getter
	@AllArgsConstructor
	public static class Post {

		/* 이메일 형식을 지켜서 작성해야 한다. */
		@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
			message = "잘못된 이메일 주소입니다.")
		private String email;

		/* 이름은 공백이 아니어야 한다. */
		@NotBlank(message = "이름은 공백이 아니어야 합니다.")
		private String name;

		/* 비밀번호 형식을 지켜서 작성해야 한다. */
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$",
			message = "비밀번호는 숫자, 영어 포함 8~15자리 이내로 구성해야합니다.")
		private String password;

		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,15}$",
			message = "비밀번호는 숫자, 영어 포함 8~15자리 이내로 구성해야합니다.")
		private String passwordCheck;

		@Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
			message = "잘못된 형식의 휴대전화 번호 입니다.")
		private String phone;

		@NotBlank
		private String address;

		/* role 은 Security 하면서 작업 예정 */
		private String role;
	}
	/* 수정은 생산자 및 소비자 DTO 에서 해야하나? */

	@Builder
	@Getter
	public static class Response {
		private long memberId;
		private String email;
		private String name;
		private String phone;
		private String address;
		/* role 은 Security 하면서 작업 예정 */
	}
}
