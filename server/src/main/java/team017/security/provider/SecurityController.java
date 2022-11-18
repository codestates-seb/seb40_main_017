package team017.security.provider;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.member.entity.Member;
import team017.member.service.MemberService;
import team017.security.dto.LoginRequestDto;
import team017.security.dto.LoginResponse;
import team017.security.dto.TokenDto;
import team017.security.dto.TokenRequestDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class SecurityController {
	/*
	* 자체 로그인 : "/login"
	* 소셜 로그인 : "/login/oauth"
	* 자체 토큰 재발급 : "/reissue"
	* 소셜 토큰 재발급 : "/reissue/oauth"
	*/
	private final SecurityService securityService;
	private final MemberService memberService;
	private final LoginMapper mapper;

	/* 토큰 로그인 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto requestBody) {
		log.info("로그인 이메일 : {}", requestBody.getEmail());
		log.error("로그인 이메일 : {}", requestBody.getEmail());
		Member member = memberService.findMemberByEmail(requestBody.getEmail());
		log.info("member 정보 : {}" , member.getName());
		TokenDto tokenDto = securityService.tokenLogin(requestBody);
		LoginResponse response = mapper.loginResponseDto(member, tokenDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());

		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/* 소셜 로그인 */
	public ResponseEntity social() {
		return null;
	}

	/* 토큰 재발급 */
	@PostMapping("/reissue")
	public ResponseEntity reissue(@RequestBody TokenRequestDto requestBody) {
		TokenDto tokenDto = securityService.tokenReissue(requestBody);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());
		String message = "액세스 토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 소셜 토큰 재발급 */
	public ResponseEntity reToken() {
		return null;
	}
}
