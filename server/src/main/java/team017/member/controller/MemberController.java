package team017.member.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

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
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.dto.MemberDto;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;

@RestController
@Validated
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final MemberMapper mapper;

	/* 회원 가입 */
	@PostMapping("/signup")
	public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
		if (!requestBody.getPassword().equals(requestBody.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);
		}

		if (!requestBody.getRole().equalsIgnoreCase("CLIENT") || !requestBody.getRole().equalsIgnoreCase("SELLER")) {
			throw new RuntimeException("역할 선택이 잘못되었습니다.");
		}

		Member member = memberService.createMember(mapper.memberDtoToMember(requestBody));
		MemberDto.Response response = mapper.memberToMemberResponseDto(member);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/* 회원 탈퇴 -> DB 삭제 */
	@DeleteMapping("/{member_id}")
	public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId) {
		memberService.deleteMember(memberId);
		String message = "Success!";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
