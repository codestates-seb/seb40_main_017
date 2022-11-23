package team017.payment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.ord.service.OrdService;

@Slf4j
@Controller
@AllArgsConstructor
public class PaymentController {

    private final KakaoPayService kakaoPayService;
    private final OrdService ordService;

    //결제 요청
    @GetMapping("/order/pay/{ord_id}")
    public @ResponseBody ResponseEntity payReady(@PathVariable("ord_id") long ordId ) {

        Ord findOrd = ordService.findVerifiedOrd(ordId);

        // 카카오 결제 준비하기 -> 결제요청 service 실행
        ReadyResponseDto response = kakaoPayService.payReady(findOrd);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //결제 승인 요청
    @GetMapping("/order/pay/completed")
    public ResponseEntity payCompleted(@RequestParam("pg_token") String pgToken) {

        log.info("!결제승인 요청 시작!") ;
        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);

        // 카카오 결재 요청하기
        OrdResponseDto response = kakaoPayService.payApprove(pgToken);
        log.info("결제 완료");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 결제 취소시 실행 url
    @GetMapping("/order/pay/cancel")
    public ResponseEntity payCancel() {
        log.info("결제 취소");
        return new ResponseEntity<>("결제 취소", HttpStatus.OK);
    }

    // 결제 실패시 실행 url
    @GetMapping("/order/pay/fail")
    public ResponseEntity payFail() {
        log.info("결제 실패");
        return new ResponseEntity<>("결제 실패",HttpStatus.OK);
    }
}
