package team017.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team017.member.service.MemberService;
import team017.security.jwt.dto.LoginDto;
import team017.security.jwt.dto.LoginResponse;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.dto.TokenRequestDto;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class JwtController {

	private final JwtService jwtService;
	private final MemberService memberService;
	private final LoginMapper mapper;

	/* 로그인 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginDto.Request requestBody) {
		long memberId = memberService.findMemberIdByEmail(requestBody.getUsername());
		TokenDto tokenDto = jwtService.longin(requestBody);
		LoginResponse response = mapper.loginResponseDto(memberId, tokenDto);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());
		httpHeaders.set("Refresh", tokenDto.getRefreshToken());

		return new ResponseEntity<>(response, httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/reissue")
	public ResponseEntity reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		TokenDto tokenDto = jwtService.reissue(tokenRequestDto);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", tokenDto.getAccessToken());
		httpHeaders.set("Refresh", tokenDto.getRefreshToken());
		String message = "액세스 토큰 재발급 완료.";

		return new ResponseEntity<>(message, httpHeaders, HttpStatus.OK);
	}
}
