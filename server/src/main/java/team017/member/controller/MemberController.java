package team017.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.mail.EmailService;
import team017.member.dto.MemberDto;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;
import team017.security.jwt.dto.LoginRequestDto;
import team017.security.jwt.dto.TokenDto;
import team017.security.jwt.service.SecurityService;
import team017.security.utils.SecurityUtil;

/* 공통의 회원 클래스 컨트롤러 : 회원가입, 로그인, 회원 탈퇴 등을 구현 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final SecurityService securityService;
	private final MemberService memberService;
	private final MemberMapper mapper;
	private final EmailService emailService;

	/* 회원 가입 */
	@PostMapping("/members/signup")
	public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) throws Exception {
		memberService.correctPassword(requestBody.getPassword(), requestBody.getPasswordCheck());
		Member createMember = memberService.createMember(mapper.memberDtoToMember(requestBody));

		/* 회원가입 메세지 발송 -> 비동기 처리  */
		emailService.sendSimpleMessage(createMember.getEmail(), createMember.getName());

		/* 로그인 시도를 위한 LoginRequestDto 생성 */
		LoginRequestDto loginRequestDto = new LoginRequestDto(createMember.getEmail(), requestBody.getPassword());

		/* 로그인 */
		return login(loginRequestDto);
	}

	/* 자체 로그인 */
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestDto requestBody) {

		/* 역할 확인 및 프로바이더 확인을 위한 Member 찾기 */
		Member member = memberService.findMemberByEmail(requestBody.getEmail());
		memberService.checkLocalProvider(member.getProviderType());

		/* 로그인하여 토큰 생성 후, 헤더에 넣어주기 */
		TokenDto tokenDto = securityService.tokenLogin(requestBody);
		HttpHeaders httpHeaders = setHeader(tokenDto.getAccessToken());

		/* 역할에 따라 응답 바디가 다르므로, 나누어 주었다.*/
		if(member.getRole().equals("SELLER")) {
			return new ResponseEntity<>(mapper.memberToSellerDto(member), httpHeaders, HttpStatus.OK);
		} else if (member.getRole().equals("CLIENT")) {
			return new ResponseEntity<>(mapper.memberToClientDto(member), httpHeaders, HttpStatus.OK);
		}

		/* 어떤 것도 해당이 안 될 경우, 예외 처리 */
		throw new BusinessLogicException(ExceptionCode.LOGIN_ERROR);
	}

	/* 회원 탈퇴 -> DB 삭제 */
	@DeleteMapping("/members/{member_id}")
	public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId) {
		memberService.deleteMember(memberId);
		String message = "Success!";

		return ResponseEntity.ok(message);
	}

	/* 로그아웃 */
	@GetMapping("/members/logout")
	public ResponseEntity logoutMember(HttpServletRequest request) {
		securityService.logout(request);

		return ResponseEntity.ok("Success!");
	}

	/* 로그인 헤더 설정 */
	private HttpHeaders setHeader(String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", token);

		return httpHeaders;
	}
}