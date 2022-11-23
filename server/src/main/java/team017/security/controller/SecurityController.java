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
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Member;
import team017.member.entity.ProviderType;
import team017.member.service.MemberService;
import team017.security.dto.LoginRequestDto;
import team017.security.dto.LoginResponse;
import team017.security.dto.TokenDto;
import team017.security.dto.LoginMapper;
import team017.security.dto.TokenRequestDto;
import team017.security.service.SecurityService;
import team017.security.utils.CookieUtil;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class SecurityController {
	/*
	 * 자체 로그인 : "/login"
	 * 소셜 로그인 : "/login/oauth/{provider_type}"
	 * 자체 토큰 재발급 : "/reissue"
	 * 소셜 토큰 재발급 : "/reissue/oauth"
	 */
	private final SecurityService securityService;
	private final MemberService memberService;
	private final LoginMapper mapper;

	/* 🔴 자체 토큰 로그인 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto requestBody) {
		Member member = memberService.findMemberByEmail(requestBody.getEmail());
		if (member.getProviderType() != ProviderType.LOCAL) {
			throw new RuntimeException("잘못된 프로바이더입니다.");
		}

		TokenDto tokenDto = securityService.tokenLogin(requestBody);

		HttpHeaders httpHeaders = setHeader(tokenDto.getAccessToken());

		if(member.getRole().equals("SELLER")) {
			LoginResponse.Seller sellerResponse =
				mapper.loginSellerResponseDto(member, tokenDto);

			return new ResponseEntity<>(sellerResponse, httpHeaders, HttpStatus.OK);
		} else if (member.getRole().equals("CLIENT")) {
			LoginResponse.Cilent clientResponse =
				mapper.loginClientResponseDto(member, tokenDto);

			return new ResponseEntity<>(clientResponse, httpHeaders, HttpStatus.OK);
		}

		throw new BusinessLogicException(ExceptionCode.LOGIN_ERROR);
	}

	/* 🔵 자체 로그  토큰 재발급 */
	@PostMapping("/reissue")
	public ResponseEntity reissue(@RequestBody TokenRequestDto requestBody) {
		TokenDto tokenDto = securityService.tokenReissue(requestBody);

		HttpHeaders httpHeaders = setHeader(tokenDto.getAccessToken());
		String message = "액세스 토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 🟢 소셜 토큰 재발급 */
	@PostMapping("/reissue/oauth")
	public ResponseEntity reToken(HttpServletRequest request, HttpServletResponse response) {
		TokenDto tokenDto = securityService.socialReissue(request, response);

		HttpHeaders httpHeaders = setHeader(tokenDto.getAccessToken());
		String message = "액세스 토큰 재발급 완료.";

		int cookieMaxAge = 1000 * 60 * 24 * 7 ;
		CookieUtil.deleteCookie(request, response, "Refresh");
		CookieUtil.addCookie(response, "Refresh", tokenDto.getRefreshToken(), cookieMaxAge);

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 새로 고침 */
	@GetMapping("/access")
	public ResponseEntity reGet(HttpServletRequest request,Principal principal) {
		String accessToken = request.getHeader("Authorization");
		HttpHeaders httpHeaders = setHeader(accessToken);
		Member member = memberService.findMemberByEmail(principal.getName());

		if(member.getRole().equals("SELLER")) {
			LoginResponse.Seller sellerResponse =
				mapper.getSellerToken(member,accessToken);

			return new ResponseEntity<>(sellerResponse, HttpStatus.OK);
		} else if (member.getRole().equals("CLIENT")) {
			LoginResponse.Cilent clientResponse =
				mapper.getClientToken(member, accessToken);

			return new ResponseEntity<>(clientResponse, httpHeaders, HttpStatus.OK);
		}

		throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS);
	}

	/* 로그인 헤더 설정 */
	private HttpHeaders setHeader(String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", token);

		return httpHeaders;
	}
}