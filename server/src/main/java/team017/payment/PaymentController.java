package team017.payment;

import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.ord.service.OrdService;

@Slf4j
@Controller
@AllArgsConstructor
@Validated
public class PaymentController {
    private final KakaoPayService kakaoPayService;
    private final OrdService ordService;

    //결제 요청
    @GetMapping("/order/pay/{ord_id}")
    public @ResponseBody ResponseEntity payReady(@PathVariable("ord_id") @Positive long ordId ) {

        Ord findOrd = ordService.findVerifiedOrd(ordId);

        // 카카오 결제 준비하기 -> 결제요청 service 실행
        ReadyResponseDto response = kakaoPayService.payReady(findOrd);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //결제 승인 요청
    @GetMapping("/order/pay/completed/{ord_id}")
    public String payCompleted( @RequestParam("pg_token") String pgToken,
                                @PathVariable("ord_id") long ordId, RedirectAttributes redirectAttributes) {

        log.info("!결제승인 요청 시작!") ;
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);

        // 카카오 결재 요청하기
        kakaoPayService.payApprove(ordId, pgToken);
        log.info("결제 완료");

        redirectAttributes.addAttribute("ordId", ordId);

        return "redirect:https://www.17farm.shop/order/pay/completed";
    }

    // 결제 취소시 실행 url
    @GetMapping("/order/pay/cancel/{ord_id}")
    public String payCancel(@PathVariable("ord_id") long ordId) {
        kakaoPayService.cancelOrFailPayment(ordId);
        log.info("결제 취소");

        return "redirect:https://www.17farm.shop/order/pay/cancel";
    }

    // 결제 실패시 실행 url
    @GetMapping("/order/pay/fail/{ord_id}")
    public String payFail(@PathVariable("ord_id") long ordId) {
        kakaoPayService.cancelOrFailPayment(ordId);
        log.info("결제 실패");

        return "redirect:https://www.17farm.shop/order/pay/fail";
    }
}
