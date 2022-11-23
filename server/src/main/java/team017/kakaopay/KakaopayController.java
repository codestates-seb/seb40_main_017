package team017.kakaopay;

import javax.validation.constraints.Positive;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.ord.entity.Ord;
import team017.ord.service.OrdService;

@Slf4j
@RestController
@RequestMapping("/order/pay")
@RequiredArgsConstructor
@Validated
public class KakaopayController {

	private final KakaopayService kakaopayService;
	private final OrdService ordService;

	/* 카카오 페이 결제 요청 */
	@GetMapping("/{ord_id}")
	public ReadyResponse payReady(@PathVariable("ord_id") @Positive long ordId, Model model) {
		log.info("# 카카오 페이 결제 요청 컨트롤러 접근");
		Ord ord = ordService.findOrd(ordId);
		log.info("주문 제품 : {}", ord.getProduct().getBoard().getTitle());
		log.info("주문 개수 : {}", ord.getTotalQuantity());
		ReadyResponse response = kakaopayService.payReady(ord);

		/* 결재 정보 */
		model.addAttribute("tid", response.getTid());
		log.info("결제 고유 번호 : {}", response.getTid());
		model.addAttribute("ord", ord);

		return response;
	}

	/* 결제 승인 요청 */
	@GetMapping("/completed")
	public String payComplete(@RequestParam("pg_token") String pgToken, @ModelAttribute("tid") String tid,
			@ModelAttribute("ord") Ord ord, Model model) {

		/* 카카오 결제 요청 */
		ApproveResponse response = kakaopayService.payApprove(tid, pgToken, ord);

		/* payment 저장? */
		Payment payment =
			Payment.builder()
				.itemName(response.getItem_name())
				.paymentMethodType(response.getPayment_method_type())
				.payCode(tid)
				.build();
		/* 해당 정보를 주문에 저장? */

		/* 주문 조회 페이지로 리다이렉트? */
		return "redirect:/mypage/" + ord.getOrdId();
	}

	/* 결제 취소 요청 -> 주문 내역 삭제 */
	@DeleteMapping("/cancel")
	public String payCancel(Ord ord) {
		/* order 서비스에서 삭제하는 메서드를 만든 후 그곳으로 돌리면 되지 않을까. */
		long boardId = ord.getProduct().getBoard().getBoardId();

		return "redirect:/boards/"+ boardId +"/orders";
	}

	/* 결제 실패 */
	@GetMapping("/fail")
	public String payFail(Ord ord) {
		long boardId = ord.getProduct().getBoard().getBoardId();

		return "redirect:/boards/"+ boardId +"/orders";
	}
}
