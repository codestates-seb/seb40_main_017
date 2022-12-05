package team017.security.oauth.info;

import java.util.Map;

import team017.member.entity.ProviderType;

public class OAuth2UserInfoFactory {
	public static OAuth2UserInfo getOAuth2UserInfo(ProviderType providerType, Map<String, Object> attributes) {
		switch (providerType) {
			// case NAVER: return new NaverOAuth2UserInfo(attributes);
			case KAKAO: return new KakaoUserInfo(attributes);
			default: throw new IllegalArgumentException("Invalid Provider Type.");
		}
	}
}

