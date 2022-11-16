package team017.security;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import team017.security.jwt.dto.LoginDto;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.dto.TokenRequestDto;
import team017.security.service.AuthService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	/* 로그인 */
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody LoginDto.Request requestBody) {
		return ResponseEntity.ok(authService.longin(requestBody));
	}

	@PostMapping("/reissue")
	public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
		return ResponseEntity.ok(authService.reissue(tokenRequestDto));
	}
}
