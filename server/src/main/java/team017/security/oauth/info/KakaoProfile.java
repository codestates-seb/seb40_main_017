package team017.security.oauth.info;

import lombok.Data;

@Data
public class KakaoProfile {

	public String id; /* 소셜 아이디로 들어갈 에정 */
	public String connected_at; //
	public Properties properties;
	public KakaoAccount kakao_account;

	public class Properties {
		public String nickname;
	}

	@Data
	public class KakaoAccount {
		public Boolean profile_nickname_needs_agreement;
		public Boolean profile_image_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

		@Data
		public class Profile {
			public String nickname;
			public String thumbnail_image_url;
			public String profile_image_url; //이미지 경로 필드2
			public Boolean is_default_image;
		}
	}
}
