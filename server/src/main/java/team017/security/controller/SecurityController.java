package team017.security.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import team017.security.dto.LoginMapper;
import team017.security.service.SecurityService;
import team017.security.utils.CookieUtil;

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

	/* 🔴 자체 토큰 로그인 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto requestBody) {
		log.info("#Controller 로그인 이메일 : {}", requestBody.getEmail());
		// log.error("#Controller 로그인 이메일 : {}", requestBody.getEmail());
		Member member = memberService.findMemberByEmail(requestBody.getEmail());
		log.info("#Controller member 정보 : {}" , member.getName());
		// log.error("#Controller member 정보 : {}" , member.getName());
		TokenDto tokenDto = securityService.tokenLogin(requestBody);
		LoginResponse response = mapper.loginResponseDto(member, tokenDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());

		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	/* 🟡 소셜 로그인 */
	@PostMapping("/login/oauth")
	public ResponseEntity social(HttpServletRequest request, HttpServletResponse response,
			@RequestBody LoginRequestDto requestDto) {

		TokenDto tokenDto = securityService.socialLogin(requestDto);

		int cookieMaxAge = 1000 * 60 * 24 * 7;
		CookieUtil.deleteCookie(request, response, "Refresh");
		CookieUtil.addCookie(response, "Refresh", tokenDto.getRefreshToken(), cookieMaxAge);

		/* memberId 를 줘야하나? */
		Member member = memberService.findMemberByEmail(requestDto.getEmail());
		LoginResponse responseDto = mapper.loginResponseDto(member, tokenDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());

		return new ResponseEntity<>(responseDto, httpHeaders, HttpStatus.OK);
	}

	@GetMapping("/")
	public String home() {
		return "Hello 17farm!";
	}

	/* 🔵 자체 로그  토큰 재발급 */
	@PostMapping("/reissue")
	public ResponseEntity reissue(@RequestBody TokenRequestDto requestBody) {
		TokenDto tokenDto = securityService.tokenReissue(requestBody);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());
		String message = "액세스 토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 🟢 소셜 토큰 재발급 */
	@PostMapping("/reissue/oauth")
	public ResponseEntity reToken(HttpServletRequest request, HttpServletResponse response) {
		TokenDto tokenDto = securityService.socialReissue(request, response);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());
		String message = "액세스 토큰 재발급 완료.";

		int cookieMaxAge = 1000 * 60 * 24 * 7 ;
		CookieUtil.deleteCookie(request, response, "Refresh");
		CookieUtil.addCookie(response, "Refresh", tokenDto.getRefreshToken(), cookieMaxAge);

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 새로 고침? */
	@GetMapping("/access")
	public ResponseEntity reGet(HttpServletRequest request,Principal principal) {
		String accessToken = request.getHeader("Authorization");
		Member member = memberService.findMemberByEmail(principal.getName());
		LoginResponse response = mapper.reGetPage(member, accessToken);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
