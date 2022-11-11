package team017.member.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import team017.member.dto.MemberDto;

@RestController
@Validated
@RequestMapping("/members")
public class MemberController {

	/* 회원 가입 */
	@PostMapping("/signup")
	public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {

		return null;
	}
}
