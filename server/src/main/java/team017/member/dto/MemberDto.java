package team017.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

		/* 휴대 전화 번호는 형식을 지켜서 작성해야 한다.*/
		@Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
			message = "잘못된 형식의 휴대전화 번호 입니다.")
		private String phone;

		/* 주소는 공백이 아니어야 한다. */
		@NotBlank
		private String address;

		/* 들어오는 값으로 Authority 부여 및 DB에 역할 저장 */
		private String role;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class ClientDto{
		private long memberId;
		private long clientId;
		private String email;
		private String name;
		private String phone;
		private String address;
		private String role;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	public static class SellerDto{
		private long memberId;
		private long sellerId;
		private String email;
		private String name;
		private String phone;
		private String address;
		private String role;
		private String introduce;
	}
}