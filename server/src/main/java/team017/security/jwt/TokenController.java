package team017.security.jwt;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;
import team017.security.aop.ReissueToken;
import team017.security.jwt.dto.LoginResponse;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.service.SecurityService;

@RestController
@RequiredArgsConstructor
public class TokenController {

	private final SecurityService securityService;
	private final MemberService memberService;
	private final MemberMapper mapper;

	/* 엑세스 토큰 재발급 */
	@PostMapping("/reissue/access")
	public ResponseEntity newAccessToken(@RequestHeader("Authorization") String accessToken) {
		String responseToken = securityService.reissueAccess(accessToken);

		HttpHeaders httpHeaders = setHeader(responseToken);
		String message = "액세스 토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 리프레시 토큰 + 엑세스 토큰 재발급 */
	@PostMapping("/reissue/refresh")
	public ResponseEntity newRefreshToken(HttpServletRequest request, HttpServletResponse response) {
		TokenDto tokenDto = securityService.reissueRefresh(request, response);

		HttpHeaders httpHeaders = setHeader(tokenDto.getAccessToken());
		String message = "토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}

	/* 새로 고침 */
	@GetMapping("/access")
	// @ReissueToken /* 토큰 재발급 안함 */
	public ResponseEntity reGet(HttpServletRequest request, Principal principal) {
		Member member = memberService.findMemberByEmail(principal.getName());
		String accessToken = request.getHeader("Authorization").replace("Bearer ", "");
		HttpHeaders headers = setHeader(accessToken);

		if(member.getRole().equals("SELLER")) {
			LoginResponse.Seller sellerResponse = mapper.getSellerResponse(member);

			return new ResponseEntity<>(sellerResponse, HttpStatus.OK);
		} else if (member.getRole().equals("CLIENT")) {
			LoginResponse.Cilent clientResponse = mapper.getClientResponse(member);

			return new ResponseEntity<>(clientResponse, headers, HttpStatus.OK);
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
