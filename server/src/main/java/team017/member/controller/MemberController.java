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
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;

/* 공통의 회원 클래스 컨트롤러 : 회원가입, 회원 탈퇴 등을 구현
  (할 수 있다면 이후에 아이디 찾기, 비밀번호 찾기도 이곳에서 구현될 예정) */
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

		/* 비밀번호 확인이 안되면 예외 던지기
		  DB에 비밀번호 확인은 저장하지 않을 예정이라 Controller 에서 처리 */
		if (!requestBody.getPassword().equals(requestBody.getPasswordCheck())) {
			throw new BusinessLogicException(ExceptionCode.PASSWORD_NOT_MATCH);
		}
		/* 입력된 role 을 확인. 만약 잘못된 역할이 들어오면 예외를 던지기
		  client 와 seller 둘 다 아닐 경우이니 && */
		if (!requestBody.getRole().equalsIgnoreCase("CLIENT") &&
			!requestBody.getRole().equalsIgnoreCase("SELLER")) {
			throw new RuntimeException("역할 선택이 잘못되었습니다.");
		}

		Member member = mapper.memberDtoToMember(requestBody);

		/* role 입력이 제대로 되었다면, 각 역할의 클래스에도 저장이 되어야 함. */
		if (requestBody.getRole().equalsIgnoreCase("client")) {
			member.setClient(new Client());
			Member createMember = memberService.createMember(member);
			MemberDto.ClientDto response = mapper.memberToClientDto(createMember, createMember.getClient());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		if (requestBody.getRole().equalsIgnoreCase("seller")) {
			member.setSeller(new Seller());
			Member createMember = memberService.createMember(member);
			MemberDto.SellerDto response = mapper.memberToSellerDto(createMember, createMember.getSeller());
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}

		throw new RuntimeException("알 수 없는 에러입니다.");
	}

	/* 회원 탈퇴 -> DB 삭제 */
	@DeleteMapping("/{member_id}")
	public ResponseEntity deleteMember(@PathVariable("member_id") @Positive long memberId) {
		memberService.deleteMember(memberId);
		String message = "Success!";
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
