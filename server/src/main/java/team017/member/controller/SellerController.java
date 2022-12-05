package team017.member.controller;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.board.Dto.BoardForSellerMyPageDto;
import team017.global.response.MultiSellerResponseDto;
import team017.member.dto.MemberDto;
import team017.member.dto.SellerPatchDto;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.mapper.MemberMapper;
import team017.member.service.MemberService;
import team017.member.service.SellerService;

import java.util.List;

/* 생산자 관련 컨트롤러 : 마이페이지 조회, 정보 수정 */
@RestController
@Slf4j
@RequestMapping("/members/seller")
@RequiredArgsConstructor
public class SellerController {
	private final SellerService sellerService;
	private final MemberService memberService;
	private final MemberMapper mapper;

	/* 판매자 마이 페이지 조회 */
	@GetMapping("/{seller_id}")
	public ResponseEntity getSeller(@PathVariable("seller_id") @Positive long sellerId) {
		Member member = sellerService.findSeller(sellerId).getMember();

		List<BoardForSellerMyPageDto> boardList = sellerService.getSellerBoard(sellerId);
		MemberDto.SelleResponseDto response = mapper.memberToSellerDto(member);

		return new ResponseEntity<>(
				new MultiSellerResponseDto(response,boardList), HttpStatus.OK);
	}

	/* 판매자 정보 수정 */
	@PutMapping("/{seller_id}")
	public ResponseEntity patchSeller(@PathVariable("seller_id") @Positive long sellerId,
			@RequestBody SellerPatchDto sellerPatchDto) {
		Seller seller = sellerService.updateSeller(sellerId, mapper.sellerPatchDtoToSeller(sellerPatchDto));
		long memberId = seller.getMember().getMemberId();

		Member member = memberService.updateMember(memberId, mapper.sellerPatchDtoToMember(sellerPatchDto));

		return ResponseEntity.ok(mapper.memberToSellerDto(member));
	}
}
