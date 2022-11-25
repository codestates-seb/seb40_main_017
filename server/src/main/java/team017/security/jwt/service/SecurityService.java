package team017.security.jwt.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.repository.MemberRepository;
import team017.security.jwt.dto.LoginRequestDto;
import team017.security.oauth.dto.SocialPatchDto;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.SecurityProvider;
import team017.security.jwt.refresh.RefreshToken;
import team017.security.jwt.refresh.RefreshTokenRepository;
import team017.security.utils.CookieUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final SecurityProvider securityProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final MemberRepository memberRepository;

	/* 자체 로그인 */
	public TokenDto tokenLogin(LoginRequestDto loginRequest) {

		/* 로그인 기반으로 "Authentication" 토큰 생성 */
		UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

		/* 이 인증 정보가 계속 null 발생 */
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		/* Access Token 및 Refresh Token 생성 */
		TokenDto tokenDto = securityProvider.generatedTokenDto(authentication.getName());

		/* Refresh Token 저장 */
		RefreshToken refreshToken =
			RefreshToken.builder()
				.key(authentication.getName())
				.value(tokenDto.getRefreshToken())
				.build();
		refreshTokenRepository.save(refreshToken);

		return tokenDto;
	}

	/* 소셜 로그인 */
	@Transactional
	public TokenDto socialLogin(LoginRequestDto loginRequest) {
		log.debug("# SecurityService Social Login 시작");

		/* 토큰 생성 -> 소셜이라는 권한 부여 */
		TokenDto tokenDto = securityProvider.generatedTokenDto(loginRequest.getEmail());

		RefreshToken userRefreshToken = refreshTokenRepository.findRefreshTokenByKey(loginRequest.getEmail());
		if (userRefreshToken == null) {

			/* 토큰이 저장되어 있지 않다면, 등록 */
			userRefreshToken = RefreshToken.builder()
				.key(loginRequest.getEmail())
				.value(tokenDto.getRefreshToken())
				.build();
			refreshTokenRepository.saveAndFlush(userRefreshToken);
		} else {

			/* 저장되어 있다면, 업데이트 */
			userRefreshToken.updateValue(tokenDto.getRefreshToken());
			refreshTokenRepository.saveAndFlush(userRefreshToken);
		}

		return tokenDto;
	}

	/* 소셜 로그인 권한 선택 */
	public Member updateSocial(SocialPatchDto patchDto) {

		Member member = memberRepository.findById(patchDto.getMemberId())
				.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
		checkSocialRole(member.getRole());

		if (patchDto.getRole().equalsIgnoreCase("CLIENT")) {
			member.setRole("CLIENT");
			member.setRoles(List.of("CLIENT"));
			member.setClient(new Client());
		} else if (patchDto.getRole().equalsIgnoreCase("SELLER")) {
			member.setRole("SELLER");
			member.setRoles(List.of("SELLER"));
			member.setSeller(new Seller());
		} else {
			throw new RuntimeException("수정할 수 없습니다.");
		}

		return member;
	}


	/* 🔵 엑세스 토큰 재발급 */
	@Transactional
	public String reissueAccess(String accessToken) {

		/* Access Token 검증 */
		if (!securityProvider.validate(accessToken)) {
			throw new RuntimeException("유효하지 않은 RefreshToken 입니다.");
		}

		/* 인증 정보 가져오기 */
		Authentication authentication = securityProvider.getAuthentication(accessToken);


		/* 토큰 유효 시간 */
		Date now = new Date();
		long accessValidTime = securityProvider.getTokenClaims(accessToken).getExpiration().getTime() - now.getTime();

		/* 엑세스 토큰의 시간이 10분 이내로 남았다면 재 발급 */
		if (accessValidTime <= 1000 * 60 * 10) {
			log.info("# 엑세스 토큰 재 발급");
			log.info("# 재발급 전 엑세스 토큰 : {}", accessToken);

			String role = authentication.getAuthorities().toString().replace("[ROLE_","").replace("]", "");

			Date newAccessExpiration = new Date(now.getTime() + securityProvider.getAccessTokenTime());
			accessToken =
				securityProvider.createAccessToken(authentication.getName(), role, newAccessExpiration);
			log.info("authorities : {}", role);
			log.info("# 재발급 엑세스 토큰 : {}", accessToken);
		}

		/* 토큰 발급 */
		return accessToken;
	}

	/* 🟢 리프레시 토큰 재발급 */
	@Transactional
	public TokenDto reissueRefresh(HttpServletRequest request, HttpServletResponse response) {

		/* Access Token 확인 */
		String accessToken = request.getHeader("Authorization");

		if (!securityProvider.validate(accessToken)) {
			throw new RuntimeException("유효하지 않은 엑세스 토큰");
		}

		Map<String, Object> claims = securityProvider.parseClaims(accessToken);

		if (claims == null) {
			throw new RuntimeException("만료되지 않은 토큰");
		}

		String username = (String)claims.get("username");

		/* Refresh Token 확인 */
		String refreshToken = CookieUtil.getCookie(request, "Refresh")
			.map(Cookie::getValue)
			.orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

		RefreshToken userRefreshToken =
			refreshTokenRepository.findByKey(username)
				.orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

		if (securityProvider.validate(refreshToken)) {
			throw  new RuntimeException("유효하지 않은 리프레시 토큰");
		}
		Date now = new Date();
		long accessTime = securityProvider.getAccessTokenTime();
		Date newAccessTime = new Date(now.getTime() + accessTime);

		long validTime = securityProvider.getTokenClaims(refreshToken).getExpiration().getTime() - now.getTime();

		/* 리프레시 토큰이 하루 이내로 남았다면, 재 발급 (엑세스 토큰도 같이 재발급) */
		if (validTime <= 1000 * 60 * 60 * 24) {

			TokenDto tokenDto = securityProvider.generatedTokenDto(username);

			accessToken = tokenDto.getAccessToken();
			refreshToken = tokenDto.getRefreshToken();

			/* Refresh DB update */
			userRefreshToken.updateValue(refreshToken);
			refreshTokenRepository.saveAndFlush(userRefreshToken);

			int cookieMaxAge = (int) 1000 * 60 * 24 * 7;
			CookieUtil.deleteCookie(request, response, "Refresh");
			CookieUtil.addCookie(response, "Refresh", refreshToken, cookieMaxAge);
		}

		TokenDto tokenDto =
			TokenDto.builder()
				.grantType("Bearer ")
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.accessTokenExpiresIn(newAccessTime.getTime())
				.build();

		return tokenDto;
	}

	/* social 로그인 멤버 확인 메서드 */
	public void correctMember(long memberId, long target) {
		if (memberId != target) {
			throw new RuntimeException("해당 소셜 회원이 아닙니다.");
		}
	}

	/* social 역할 확인 */
	private void checkSocialRole(String role) {
		if (!role.equalsIgnoreCase("SOCIAL")) {
			throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS);
		}
	}
}
