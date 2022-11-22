package team017.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Member;
import team017.member.entity.ProviderType;
import team017.member.repository.MemberRepository;
import team017.security.dto.KakaoToken;
import team017.security.info.KakaoProfile;
import team017.security.info.KakaoUserInfo;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {
	private final MemberRepository memberRepository;

	private final String clientId = "0ec82f5e7c4e00ccf807f4b8f98fe01c";

	private final String clientSecret = "767fTk4HHcrA84bzBYAMedmdaev5fGKc";

	private final String redirectUri = "http://localhost:8080/login/oauth2/code/kakao";

	private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";

	private final String userInfoUri = "https://kapi.kakao.com/v2/user/me";

	/* 엑세스 토큰 from Kakao */
	public KakaoToken getAccessToken(String code) {

		/* RequestParam */
		MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		param.add("grant_type", "authorization_code");
		param.add("client_id", clientId);
		param.add("redirect_uri", redirectUri);
		param.add("code", code);
		param.add("client_secret", clientSecret);

		/* 요청 */
		WebClient wc = WebClient.create(accessTokenUri);

		String response = wc.post()
			.uri(accessTokenUri)
			.body(BodyInserters.fromFormData(param))
			.header("Content-type","application/x-www-form-urlencoded;charset=utf-8")
			.retrieve()
			.bodyToMono(String.class)
			.block();

		/* Json 으로 변환 */
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoToken kakaoToken = null;

		try {
			kakaoToken = objectMapper.readValue(response, KakaoToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoToken;
	}

	/* 사용자 정보 가져오기 */
	public KakaoProfile findProfile(String token) {
		WebClient wc = WebClient.create(userInfoUri);

		String response = wc.post()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.bodyToMono(String.class)
			.block();

		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoProfile;
	}

	/* 카카오 로그인 사용자 강제 회원 가입 */
	@Transactional
	public Member saveMember(String access_token) {
		KakaoProfile profile = findProfile(access_token); //사용자 정보 받아오기
		Member member = memberRepository.findMemberByEmail(profile.getKakao_account().getEmail());

		//처음이용자 강제 회원가입
		if(member ==null) {
			member = Member.builder()
				.socialId(profile.getId())
				.password(null) //필요없으니 일단 아무거도 안넣음. 원하는데로 넣으면 됌
				.name(profile.getKakao_account().getProfile().getNickname())
				.email(profile.getKakao_account().getEmail())
				.role("SOCIAL")
				.roles(List.of("SOCIAL"))
				.providerType(ProviderType.KAKAO)
				.build();

			memberRepository.save(member);
		}

		return member;
	}
}
