package team017.member.controller;

import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.member.dto.MemberDto;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;
import team017.security.dto.LoginRequestDto;
import team017.security.dto.TokenDto;
import team017.security.service.SecurityService;

/* 공통의 회원 클래스 컨트롤러 : 회원가입, 회원 탈퇴 등을 구현 */
@Slf4j
@RestController
@Validated
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper mapper;
	private final SecurityService securityService;

	/* 회원 가입 */
	@PostMapping("/signup")
	public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody, HttpServletRequest request) throws
		ServletException {

		memberService.correctPassword(requestBody.getPassword(), requestBody.getPasswordCheck());
		memberService.correctRole(requestBody.getRole());
		Member createMember = memberService.createMember(mapper.memberDtoToMember(requestBody));


		/* 회원 가입 후 자동 로그인? */
		log.info("# 로그인 시도 로직 시작 ");
		request.login(createMember.getEmail(), requestBody.getPassword());
		Principal principal = request.getUserPrincipal();
		LoginRequestDto loginRequestDto =
			LoginRequestDto.builder()
				.email(principal.getName())
				.password(requestBody.getPassword())
				.build();
		TokenDto tokenDto = securityService.tokenLogin(loginRequestDto);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", tokenDto.getAccessToken());
		log.info("# 회원 이메일 : {}", principal.getName());

		if (requestBody.getRole().equalsIgnoreCase("seller")) {
			return new ResponseEntity<>(mapper.memberToSellerDto(createMember, tokenDto), headers, HttpStatus.CREATED);
		}
		return new ResponseEntity<>(mapper.memberToClientDto(createMember, tokenDto), headers, HttpStatus.CREATED);
	}

	/* 회원 탈퇴 -> DB 삭제 */
	@DeleteMapping("/{member_id}")
	public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId) {
		memberService.deleteMember(memberId);
		String message = "Success!";

		return ResponseEntity.ok(message);
	}
}
