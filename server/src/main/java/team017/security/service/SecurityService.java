package team017.security.service;

import java.sql.Ref;
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
import team017.security.dto.LoginRequestDto;
import team017.security.dto.SocialPatchDto;
import team017.security.dto.TokenDto;
import team017.security.dto.TokenRequestDto;
import team017.security.provider.SecurityProvider;
import team017.security.refresh.RefreshToken;
import team017.security.refresh.RefreshTokenRepository;
import team017.security.utils.CookieUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final SecurityProvider securityProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final MemberRepository memberRepository;

	/* 🔴 자체 로그인 */
	@Transactional
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

	/* 🟡 소셜 로그인 */
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

		Member member =
			memberRepository.findById(patchDto.getMemberId())
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

	/* 엑세스 토큰 재발급 */
	public String getAgainAccessToken(String accessToken) {
		long validTime = securityProvider.getTokenClaims(accessToken).getExpiration().getTime();

		/* 엑세스 토큰 시간이 10분 이내로 남았다면 토큰 재발급 */
		if (validTime <= 1000 * 60 * 10) {
			Authentication authentication = securityProvider.getAuthentication(accessToken);
			RefreshToken refresh = refreshTokenRepository.findRefreshTokenByKey(authentication.getName());
			if (refresh == null) {
				throw new RuntimeException("리프레시 토큰이 존재하지 않습니다.");
			}
			long now = (new Date()).getTime();
			Date expiration = new Date(now + securityProvider.getAccessTokenTime());
			accessToken =
				securityProvider.createAccessToken(authentication.getName(), authentication.getAuthorities().toString(), expiration);
			log.info("권한 : {}", authentication.getAuthorities().toString());
		}

		return accessToken;
	}


	/* 🔵 자체 로그인 토큰 재발급 */
	@Transactional
	public TokenDto tokenReissue(TokenRequestDto tokenRequestDto) {

		/* Refresh Token 검증 */
		if (!securityProvider.validate(tokenRequestDto.getRefreshToken())) {
			throw new RuntimeException("유효하지 않은 RefreshToken 입니다.");
		}

		/* 인증 정보에서 Key 값(email) 가져오기 */
		Authentication authentication = securityProvider.getAuthentication(tokenRequestDto.getAccessToken());

		/* Key 값으로 Refresh Token 가져오기 */
		RefreshToken refreshToken =
			refreshTokenRepository.findByKey(authentication.getName())
				.orElseThrow(() -> new RuntimeException("로그아웃 된 사용자 입니다."));

		/* 해당 토큰이 일치하는지 검사 */
		if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
			throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
		}

		/* 새로운 토큰 생성 */
		TokenDto tokenDto = securityProvider.generatedTokenDto(authentication.getName());

		/* 저장 정보 업데이트 */
		RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
		refreshTokenRepository.save(newRefreshToken);

		/* 토큰 발급 */
		return tokenDto;
	}

	/* 🟢 소셜 로그인 리프레시 토큰 재발급 -> DB 에서만 관리 확인? */
	@Transactional
	public TokenDto socialReissue(HttpServletRequest request, HttpServletResponse response) {

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
		String role = (String)claims.get("role");

		/* Refresh Token 확인 */
		String refreshToken = CookieUtil.getCookie(request, "Refresh")
			.map(Cookie::getValue)
			.orElse(null);

		if (securityProvider.validate(refreshToken)) {
			throw  new RuntimeException("유효하지 않은 리프레시 토큰");
		}
		Date now = new Date();
		long accessTime = securityProvider.getAccessTokenTime();
		Date newAccessTime = new Date(now.getTime() + accessTime);

		String newAccessToken = securityProvider.createAccessToken(username, role, newAccessTime);

		RefreshToken userRefreshToken = refreshTokenRepository.findByKeyAndValue(username, refreshToken);


		long validTime = securityProvider.getTokenClaims(refreshToken).getExpiration().getTime();

		/* 리프레시 토큰이 3일 이내로 남았다면, 재 발급 */
		if (validTime <= 259200000) {

			long refreshTokenExpiration = securityProvider.getRefreshTokenTime();
			Date newExpiration = new Date(now.getTime() + refreshTokenExpiration);

			refreshToken = securityProvider.createRefreshToken(username, newExpiration);

			/* Refresh DB update */
			userRefreshToken.updateValue(refreshToken);

			int cookieMaxAge = (int) refreshTokenExpiration / 60;
			CookieUtil.deleteCookie(request, response, "Refresh");
			CookieUtil.addCookie(response, "Refresh", refreshToken, cookieMaxAge);
		}

		TokenDto tokenDto =
			TokenDto.builder()
				.grantType("Bearer ")
				.accessToken(newAccessToken)
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
