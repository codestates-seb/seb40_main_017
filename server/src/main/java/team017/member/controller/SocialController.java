package team017.member.controller;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.security.oauth.dto.KakaoToken;
import team017.security.jwt.dto.LoginRequestDto;
import team017.security.jwt.dto.LoginResponse;
import team017.security.oauth.dto.SocialPatchDto;
import team017.security.jwt.dto.TokenDto;
import team017.security.oauth.service.KakaoService;
import team017.security.jwt.service.SecurityService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SocialController {
	private final SecurityService securityService;

	private final KakaoService kakaoService;
	private final MemberMapper mapper;

	/* 카카오 로그인 */
	/* frontend 로 부터 받은 인가 코드 받기 및 사용자 정보 받기, 회원가입 */
	@GetMapping("/login/oauth2/code/kakao")
	public String KakaoLogin(@RequestParam("code") String code) throws Exception {
		log.info("카카오에서 인가코드 발급 후 백엔드로 들어옴");
		log.error("카카오에서 인가코드 발급 후 백엔드로 들어옴");

		/* access 토큰 받기 */
		KakaoToken oauthToken = kakaoService.getAccessToken(code);

		log.info("소셜 컨트롤러, 액세스 토큰 발급 완료 : {}", oauthToken.getAccess_token());
		log.error("소셜 컨트롤러, 액세스 토큰 발급 완료 : {}", oauthToken.getAccess_token());
		log.info("컨트롤러 -> 서비스 : 사용자 정보 받고 회원가입");
		log.error("컨트롤러 -> 서비스 : 사용자 정보 받고 회원가입");

		/* 사용자 정보받기 및 회원가입 */
		Member member = kakaoService.saveMember(oauthToken.getAccess_token());

		/* jwt 토큰 저장 */
		LoginRequestDto requestDto = new LoginRequestDto(member.getEmail(), member.getPassword());
		TokenDto tokenDto = securityService.socialLogin(requestDto);
		// LoginResponse.Member response = mapper.socialLoginResponseDto(member, tokenDto.getAccessToken());

		/* 아래는 로그인이 잘 된다면, 배포용 */
		if (member.getRole().equalsIgnoreCase("SOCIAL")) {
			URI uri = UriComponentsBuilder.fromUri(URI.create("https://www.17farm.shop/social"))
				.queryParam("accessToken", tokenDto.getAccessToken())
				.queryParam("memberId", member.getMemberId())
				.build().toUri();
			return "redirect:" + uri;
		}
		URI uri = UriComponentsBuilder.fromUri(URI.create("https://www.17farm.shop/social"))
			.queryParam("accessToken", tokenDto.getAccessToken())
			.build().toUri();

		return "redirect:" + uri;
	}

	/* 소셜 로그인 수정 -> only 권한 */
	@PutMapping("/social/{member_id}")
	public ResponseEntity patchSocial(@PathVariable("member_id") long memberId,
			@RequestBody SocialPatchDto patchDto) {
		Member member = securityService.updateSocial(patchDto.getRole(), memberId);
		LoginRequestDto request = new LoginRequestDto(member.getEmail(), member.getPassword());

		/* 여기서 토큰 재발급 */
		TokenDto tokenDto = securityService.socialLogin(request);
		HttpHeaders httpHeaders = setHeader(tokenDto);

		if (member.getRole().equals("SELLER")) {
			return new ResponseEntity<>(mapper.memberToSellerDto(member),httpHeaders, HttpStatus.OK);
		} else if (member.getRole().equals("CLIENT")) {
			return new ResponseEntity<>(mapper.memberToClientDto(member), httpHeaders, HttpStatus.OK);
		}
		throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS);
	}

	/* 로그인 헤더 설정 */
	private HttpHeaders setHeader(TokenDto tokenDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());

		return httpHeaders;
	}
}
