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
import team017.member.dto.MemberDto;
import team017.member.entity.Member;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;

/* 공통의 회원 클래스 컨트롤러 : 회원가입, 회원 탈퇴 등을 구현 */
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

		memberService.correctPassword(requestBody.getPassword(), requestBody.getPasswordCheck());
		memberService.correctRole(requestBody.getRole());
		Member createMember = memberService.createMember(mapper.memberDtoToMember(requestBody));

		if (requestBody.getRole().equalsIgnoreCase("seller")) {
			return new ResponseEntity<>(mapper.memberToSellerDto(createMember, createMember.getSeller()), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(mapper.memberToClientDto(createMember, createMember.getClient()), HttpStatus.CREATED);
	}

	/* 회원 탈퇴 -> DB 삭제 */
	@DeleteMapping("/{member_id}")
	public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId) {
		memberService.deleteMember(memberId);
		String message = "Success!";

		return ResponseEntity.ok(message);
	}
}
