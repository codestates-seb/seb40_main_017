package team017.security.oauth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
		// MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
		// param.add("grant_type", "authorization_code");
		// param.add("client_id", clientId);
		// param.add("redirect_uri", redirectUri);
		// param.add("code", code);
		// param.add("client_secret", clientSecret);
		//
		// /* 요청 */
		// WebClient wc = WebClient.create(accessTokenUri);
		//
		// String response = wc.post()
		// 	.uri(accessTokenUri)
		// 	.body(BodyInserters.fromFormData(param))
		// 	.header("Content-type","application/x-www-form-urlencoded;charset=utf-8")
		// 	.retrieve()
		// 	.bodyToMono(String.class)
		// 	.block();

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers  = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		// 일단 param들 변수화 안함.. 추후 예정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);
		params.add("client_secret", clientSecret);

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담는다
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
			new HttpEntity<>(params, headers);

		// 실제요청
		ResponseEntity<String> response = restTemplate.exchange(
			"https://kauth.kakao.com/oauth/token",
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);

		/* Json 으로 변환 */
		ObjectMapper objectMapper = new ObjectMapper();
		KakaoToken kakaoToken = null;

		try {
			kakaoToken = objectMapper.readValue(response.getBody(), KakaoToken.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return kakaoToken;
	}

	/* 사용자 정보 가져오기 */
	public KakaoProfile findProfile(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers  = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
			new HttpEntity<>(headers);

		ResponseEntity<String> response = restTemplate.exchange(
			userInfoUri,
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);

		// WebClient wc = WebClient.create(userInfoUri);
		//
		// String response = wc.post()
		// 	.uri(userInfoUri)
		// 	.header("Authorization", "Bearer " + token)
		// 	.retrieve()
		// 	.bodyToMono(String.class)
		// 	.block();

		ObjectMapper objectMapper = new ObjectMapper();
		KakaoProfile kakaoProfile = null;

		try {
			kakaoProfile = objectMapper.readValue(response.getBody(), KakaoProfile.class);

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
				.password("소셜 로그인임")
				.name(profile.getKakao_account().getProfile().getNickname())
				.email(profile.getKakao_account().getEmail())
				// .role("SOCIAL")
				.role("CLIENT")
				// .roles(List.of("SOCIAL"))
				.roles(List.of("CLIENT"))
				.providerType(ProviderType.KAKAO)
				.build();

			memberRepository.save(member);
		}

		return member;
	}
}
