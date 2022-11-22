package team017.security.controller;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Member;
import team017.security.dto.KakaoToken;
import team017.security.dto.LoginMapper;
import team017.security.dto.LoginRequestDto;
import team017.security.dto.LoginResponse;
import team017.security.dto.TokenDto;
import team017.security.service.KakaoService;
import team017.security.service.SecurityService;

@RestController
@RequiredArgsConstructor
public class SocialController {
	private final SecurityService securityService;

	private final KakaoService kakaoService;
	private final LoginMapper mapper;

	/* 카카오 로그인 */

	/* frontend 로 부터 받은 인가 코드 받기 및 사용자 정보 받기,회원가입 */
	@GetMapping("/login/oauth/token/kakao")
	public ResponseEntity KakaoLogin(@RequestParam("code") String code) {

		/* access 토큰 받기 */
		KakaoToken oauthToken = kakaoService.getAccessToken(code);

		/* 사용자 정보받기 및 회원가입 */
		Member member = kakaoService.saveMember(oauthToken.getAccess_token());

		/* jwt토큰 저장 */
		LoginRequestDto requestDto = new LoginRequestDto(member.getEmail(), member.getPassword());

		TokenDto tokenDto = securityService.socialLogin(requestDto);

		LoginResponse.Member response = mapper.socialLoginResponseDto(member, tokenDto);

		HttpHeaders httpHeaders = setHeader(tokenDto);

		return new ResponseEntity(response, httpHeaders, HttpStatus.OK);
	}

	/* test로 직접 인가 코드 받기 */
	@GetMapping("/login/oauth2/code/kakao")
	public String KakaoCode(@RequestParam("code") String code) {
		return "카카오 로그인 인증완료, code: "  + code;
	}

	/* 로그인 헤더 설정 */
	private HttpHeaders setHeader(TokenDto tokenDto) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("grantType", tokenDto.getGrantType());
		httpHeaders.set("Authorization", tokenDto.getAccessToken());

		return httpHeaders;
	}
}
