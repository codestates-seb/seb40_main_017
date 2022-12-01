package team017.security.oauth.info;

import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo{

	public KakaoUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return attributes.get("id").toString();
	}

	@Override
	public String getName() {
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

		if (properties == null) {
			return null;
		}

		return (String) properties.get("nickname");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("account_email");
	}
}
