package team017.payment;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@SessionAttributes({"tid","order"})
@AllArgsConstructor
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    // 카카오페이결제 요청
    @GetMapping("/order/pay")
    @ResponseBody
    public ResponseEntity payReady(@RequestParam(name = "total_amount") int totalAmount) {

//        log.info("주문정보:"+order);
        log.info("주문가격:"+totalAmount);

        // 카카오 결제 준비하기	- 결제요청 service 실행.
        ReadyResponseDto response = kakaoPayService.payReady(totalAmount);

//        // 요청처리후 받아온 결재고유 번호(tid)를 모델에 저장
//        model.addAttribute("tid", response.getTid());
//        log.info("결재고유 번호: " + response.getTid());
//
//        // Order정보를 모델에 저장
//        model.addAttribute("order",order);

        // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.)

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

     //결제승인요청
    @GetMapping("/order/pay/completed")
    public ResponseEntity payCompleted(@RequestParam("pg_token") String pgToken, @RequestParam("tid") String tid) {

        log.info("결제승인 요청을 인증하는 토큰: " + pgToken);
        //log.info("주문정보: " + order);
        log.info("결재고유 번호: " + tid);

        // 카카오 결재 요청하기
        ApproveResponseDto approveResponse = kakaoPayService.payApprove(tid, pgToken);

//        // 5. payment 저장
//        //	orderNo, payMathod, 주문명.
//        // - 카카오 페이로 넘겨받은 결재정보값을 저장.
//        Payment payment = Payment.builder()
//                .paymentClassName(approveResponse.getItem_name())
//                .payMathod(approveResponse.getPayment_method_type())
//                .payCode(tid)
//                .build();
//
//        orderService.saveOrder(order,payment);
//
//        return "redirect:/orders";
        return new ResponseEntity<>(approveResponse, HttpStatus.OK);
    }


    // 결제 취소시 실행 url
    @GetMapping("/order/pay/cancel")
    public ResponseEntity payCancel() {
        return new ResponseEntity<>("결제 취소", HttpStatus.OK);
    }

    // 결제 실패시 실행 url
    @GetMapping("/order/pay/fail")
    public ResponseEntity payFail() {
        return new ResponseEntity<>("결제 실패",HttpStatus.OK);
    }
}
