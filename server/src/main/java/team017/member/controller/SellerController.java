package team017.member.controller;

import javax.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
import team017.security.aop.ReissueToken;

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
	@ReissueToken
	public ResponseEntity getSeller(@PathVariable("seller_id") @Positive long sellerId) {
		Member member = sellerService.findSeller(sellerId).getMember();

		List<BoardForSellerMyPageDto> boardList = sellerService.getSellerBoard(sellerId);
		MemberDto.SelleResponseDto response = mapper.memberToSellerDto(member);

		return new ResponseEntity<>(
				new MultiSellerResponseDto(response,boardList), HttpStatus.OK);
	}

	/* 판매자 정보 수정 */
	@PatchMapping("/{seller_id}")
	@ReissueToken
	public ResponseEntity patchSeller(@PathVariable("seller_id") @Positive long sellerId,
			@RequestBody SellerPatchDto sellerPatchDto) {
		log.info("# 수정 컨트롤러 시작");
		log.info("# 1. 생산자 정보 수정 -> url selleId 확인 : {}", sellerId);
		Seller seller = sellerService.updateSeller(sellerId, mapper.sellerPatchDtoToSeller(sellerPatchDto));
		log.info("# 생산자 정보 수정 서비스 끝");
		log.info("# 2. 생산자 정보 수정 -> seller 저장 후 리턴 확인 : {}", seller.getIntroduce());
		log.error("# 2. 생산자 정보 수정 -> seller 저장 후 리턴 확인 : {}", seller.getIntroduce());
		long memberId = seller.getMember().getMemberId();

		log.info("# 멤버 서비스 호출 직전");
		log.error("# 멤버 서비스 호출 직전");
		Member member = memberService.updateMember(memberId, mapper.sellerPatchDtoToMember(sellerPatchDto));
		log.info("# 멤버 수정 서비스 끝");
		log.error("# 멤버 수정 서비스 끝");
		log.info("# 3. 생산자 정보 수정 -> 멤버 업데이트 후 리턴 확인 : {}", member.getName());
		log.error("# 3. 생산자 정보 수정 -> 멤버 업데이트 후 리턴 확인 : {}",member.getName());

		return ResponseEntity.ok(mapper.memberToSellerDto(member));
	}
}
