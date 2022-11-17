package team017.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import team017.security.jwt.JwtProvider;
// import team017.security.auth.AuthToken;
import team017.security.jwt.dto.LoginDto;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.dto.TokenRequestDto;
import team017.security.refresh.RefreshToken;
import team017.security.refresh.RefreshTokenRepository;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtProvider jwtProvider;
	// private final AuthToken authToken;
	private final RefreshTokenRepository refreshTokenRepository;

	/* 로그인 하면, Token 발급 */
	@Transactional
	public TokenDto longin(LoginDto.Request loginRequest) {

		/* 로그인 기반으로 "Authentication" 토큰 생성 */
		UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

		/* 검증 -> 비밀번호 체크?
		   MemberDetailService loadByUser 작동 */
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		/* 로그인 기반으로 "JWT" 토큰 생성 */
		TokenDto tokenDto = jwtProvider.generatedTokenDto(authentication);
		// TokenDto tokenDto = authToken.generatedTokenDto(authentication);

		/* Refresh Token 저장 */
		RefreshToken refreshToken =
			RefreshToken.builder()
				.key(authentication.getName())
				.value(tokenDto.getRefreshToken())
				.build();
		refreshTokenRepository.save(refreshToken);

		/* 토큰 발급? */
		return tokenDto;
	}

	/* 토큰 재발급? */
	@Transactional
	public TokenDto reissue(TokenRequestDto tokenRequestDto) {

		/* Refresh Token 검증 */
		if (!jwtProvider.validateToken(tokenRequestDto.getRefreshToken())) {
			throw new RuntimeException("유효하지 않은 RefreshToken 입니다.");
		}
		// if (!authToken.validateToken(tokenRequestDto.getRefreshToken())) {
		// 	throw new RuntimeException("유효하지 않은 RefreshToken 입니다.");
		// }

		/* 인증 정보에서 Key 값(email) 가져오기 */
		Authentication authentication = jwtProvider.getAuthentication(tokenRequestDto.getAccessToken());
		// Authentication authentication = authToken.getAuthentication(tokenRequestDto.getAccessToken());

		/* Key 값으로 Refresh Token 가져오기 */
		RefreshToken refreshToken =
			refreshTokenRepository.findByKey(authentication.getName())
				.orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

		/* 해당 토큰이 일치하는지 검사 */
		if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
		}

		/* 새로운 토큰 생성 */
		TokenDto tokenDto = jwtProvider.generatedTokenDto(authentication);
		// TokenDto tokenDto = authToken.generatedTokenDto(authentication);

		/* 저장 정보 업데이트 */
		RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
		refreshTokenRepository.save(newRefreshToken);

		/* 토큰 발급 */
		return tokenDto;
	}
}
