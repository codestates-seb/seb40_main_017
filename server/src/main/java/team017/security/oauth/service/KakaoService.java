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
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.ProviderType;
import team017.member.repository.MemberRepository;
import team017.security.oauth.dto.KakaoToken;
import team017.security.oauth.info.KakaoProfile;

/*
 * uri 직접 입력 && web client 사용 --> yml 에서 불러올 때 자꾸 IP 주소로 불러옴 + HttpEntity 와 ResponseEntity 가 만들어지지 않음.
 * controller 에서 get 하고 redirect
 * controller 에서 get 하고 직접 다시 호출
 * 변수 값 다 그냥 직접 입력해보기
 * 안되면 멘토님 찬스
 * */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class KakaoService {
	private final MemberRepository memberRepository;

	@Value("${spring.security.oauth2.client.registration.kakao.clientId}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.kakao.clientSecret}")
	private String clientSecret;

	/* 서버에서 배포하면 ip 주소로 들어옴 -> 직접 주입 */
	private final String redirectUri = "http://17farm-server.shop:8080/login/oauth2/code/kakao";
	// private final String redirectUri = "https://17farm-server.shop:8080/login/oauth2/code/kakao";
	// private final String redirectUri = "http://localhost:8080/login/oauth2/code/kakao";
	private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";
	private final String userInfoUri = "https://kapi.kakao.com/v2/user/me";

	/* 엑세스 토큰 from Kakao */
	public KakaoToken getAccessToken(String code) {
		log.info("# 카카오 코드로 엑세스 토큰 발급 받기 , 카카오 서비스 시작");
		log.error("# 카카오 코드로 엑세스 토큰 발급 받기 , 카카오 서비스 시작");

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

		// RestTemplate restTemplate = new RestTemplate();
		// HttpHeaders headers  = new HttpHeaders();
		// headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//
		// MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		// params.add("grant_type", "authorization_code");
		// params.add("client_id", clientId);
		// params.add("redirect_uri", redirectUri);
		// params.add("code", code);
		// params.add("client_secret", clientSecret);
		//
		// log.info("리다이렉트 주소: {}", params.get("redirect_uri").toString());
		// log.error("리다이렉트 주소: {}", params.get("redirect_uri").toString());
		//
		// // HttpHeader와 HttpBody를 하나의 오브젝트로 담는다
		// HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
		// 	new HttpEntity<>(params, headers);
		//
		// log.info(kakaoTokenRequest.getBody().toString());
		// log.error(kakaoTokenRequest.getBody().toString());
		//
		// ResponseEntity<String> response = restTemplate.exchange(
		// 	accessTokenUri,
		// 	HttpMethod.POST,
		// 	kakaoTokenRequest,
		// 	String.class
		// );

		log.info(response);
		log.error(response);
		log.info(redirectUri);
		log.error(redirectUri);
		log.info(accessTokenUri);
		log.error(accessTokenUri);

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

		log.info("사용자 정보 가져오기 : {}", token);
		log.error("사용자 정보 가져오기 : {}", token);

		// RestTemplate restTemplate = new RestTemplate();
		// HttpHeaders headers  = new HttpHeaders();
		// headers.add("Authorization", "Bearer " + token);
		// headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//
		// HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
		// 	new HttpEntity<>(headers);
		//
		// ResponseEntity<String> response = restTemplate.exchange(
		// 	userInfoUri,
		// 	HttpMethod.POST,
		// 	kakaoTokenRequest,
		// 	String.class
		// );

		WebClient wc = WebClient.create(userInfoUri);

		String response = wc.post()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + token)
			.retrieve()
			.bodyToMono(String.class)
			.block();

		log.info("find profile response entity exchange");
		log.error("find profile response entity exchange");
		log.info(response);
		log.error(response);

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
		log.info("카카오 서비스 save member 시작");
		log.error("카카오 서비스 save member 시작");

		KakaoProfile profile = findProfile(access_token); /* 사용자 정보 받아오기 */
		Member member = memberRepository.findMemberByEmail(profile.getKakao_account().getEmail());

		/* 첫 이용자 강제 회원가입 -> 우선 구매자로 한정 배정하기로 함. 만약 프런트에서 소셜 권한에서 수정하는 페이지가 된다고 하면 바뀔 예정  */
		if(member == null) {
			log.info("사용자 강제 가입");
			log.error("사용자 강제 가입");
			member = new Member(
				profile.getKakao_account().getProfile().getNickname(),
				profile.getKakao_account().getEmail(),
				"소셜 로그인 사용자", /* 비밀번호 */
				ProviderType.KAKAO,
				"CLIENT",
				List.of("CLIENT"),
				profile.getId()
 			);
			member.setClient(new Client());

			memberRepository.save(member);
		}
		log.info("return member : {}", member.getName());
		log.error("return member : {}", member.getName());

		return member;
	}
}
