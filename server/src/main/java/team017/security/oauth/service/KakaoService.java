package team017.security.oauth.service;

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
import team017.security.oauth.dto.KakaoToken;
import team017.security.oauth.info.KakaoProfile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {
	private final MemberRepository memberRepository;

	@Value("${spring.security.oauth2.client.registration.kakao.clientId}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.kakao.redirectUri}")
	private String redirectUri;

	@Value("${spring.security.oauth2.client.provider.kakao.tokenUri}")
	private String accessTokenUri;

	@Value("${spring.security.oauth2.client.provider.kakao.userInfoUri}")
	private String userInfoUri;

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

		/* 첫 이용자 강제 회원가입 */
		if(member == null) {
			member = Member.builder()
				.socialId(profile.getId())
				.password(null)
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
