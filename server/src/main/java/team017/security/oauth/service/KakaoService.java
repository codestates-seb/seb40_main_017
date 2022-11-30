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
import lombok.extern.slf4j.Slf4j;
import team017.member.entity.Member;
import team017.member.entity.ProviderType;
import team017.member.repository.MemberRepository;
import team017.security.oauth.dto.KakaoToken;
import team017.security.oauth.info.KakaoProfile;

@Service
@Slf4j
@RequiredArgsConstructor
// @Transactional
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
		log.info("# 카카오 코드로 엑세스 토큰 발급 받기 , 카카오 서비스 시작");
		log.error("# 카카오 코드로 엑세스 토큰 발급 받기 , 카카오 서비스 시작");

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

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", clientId);
		params.add("redirect_uri", redirectUri);
		params.add("code", code);
		params.add("client_secret", clientSecret);

		// HttpHeader와 HttpBody를 하나의 오브젝트로 담는다
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
			new HttpEntity<>(params, headers);

		ResponseEntity<String> response = restTemplate.exchange(
			accessTokenUri,
			HttpMethod.POST,
			kakaoTokenRequest,
			String.class
		);

		log.info(response.getBody());
		log.error(response.getBody());
		log.info(redirectUri);
		log.error(redirectUri);
		log.info(accessTokenUri);
		log.error(accessTokenUri);

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

		log.info("사용자 정보 가져오기 : {}", token);
		log.error("사용자 정보 가져오기 : {}", token);

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

		log.info("find profile response entity exchange");
		log.error("find profile response entity exchange");
		log.info(response.getBody());
		log.error(response.getBody());
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
		log.info("카카오 서비스 save member 시작");
		log.error("카카오 서비스 save member 시작");

		KakaoProfile profile = findProfile(access_token); /* 사용자 정보 받아오기 */
		Member member = memberRepository.findMemberByEmail(profile.getKakao_account().getEmail());

		/* 첫 이용자 강제 회원가입 */
		if(member == null) {
			log.info("사용자 강제 가입");
			log.error("사용자 강제 가입");
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
		log.info("return member : {}", member.getName());
		log.error("return member : {}", member.getName());

		return member;
	}
}
