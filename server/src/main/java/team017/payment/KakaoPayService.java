package team017.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayService {

    // 결제 준비 메서드
    public ReadyResponseDto payReady(int totalAmount) {

        String order_id = "Ord1";
        String itemName = "사과";

        // 서버로 요청할 Body
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");                                //가맹점 코드, 10자
        parameters.add("partner_order_id", order_id);                       //가맹점 주문번호, 최대 100자
        parameters.add("partner_user_id", "17farm");                        //가맹점 회원 id, 최대 100자
        parameters.add("item_name", itemName);                              //상품명
        parameters.add("quantity", String.valueOf(1));                   //상품 수량
        parameters.add("total_amount", String.valueOf(totalAmount));        //상품 총액
        parameters.add("tax_free_amount", "0");                             //상품 총액
        parameters.add("approval_url", "http://localhost:8080/order/pay/completed");     // 결제승인시 넘어갈 redirect url,
        parameters.add("cancel_url", "http://localhost:8080/order/pay/cancel");          // 결제취소시 넘어갈 redirect url,
        parameters.add("fail_url", "http://localhost:8080/order/pay/fail");              // 결제 실패시 넘어갈 redirect url,

        log.info("파트너주문아이디:"+ parameters.get("partner_order_id")) ;

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부url요청 통로 열기.
        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/ready";

        // template으로 값을 보내고 받아온 ReadyResponse값 readResponseDto 저장.
        ReadyResponseDto readResponseDto = template.postForObject(url, requestEntity, ReadyResponseDto.class);
        log.info("결재준비 응답객체: " + readResponseDto);

        // 받아온 값 return
        return readResponseDto;
    }

    // 결제 승인요청 메서드
    public ApproveResponseDto payApprove(String tid, String pgToken) {

        String order_id = "Ord1";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");                    //가맹점 코드
        parameters.add("tid", tid);                             //결제 고유번호, 결제 준비 API 응답에 포함
        parameters.add("partner_order_id", order_id);           //가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
        parameters.add("partner_user_id", "17farm");             //가맹점 회원 id, 결제 준비 API 요청과 일치해야 함
        parameters.add("pg_token", pgToken);                    //결제승인 요청을 인증하는 토큰
                                                                //사용자 결제 수단 선택 완료 시, approval_url로 redirection해줄 때 pg_token을 query string으로 전달

        // 하나의 map안에 header와 parameter값을 담아줌.
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부url 통신
        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/approve";

        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스.
        ApproveResponseDto approveResponse = template.postForObject(url, requestEntity, ApproveResponseDto.class);
        log.info("결재승인 응답객체: " + approveResponse);

        return approveResponse;
    }


    // 서버로 요청할 Header
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "");
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }
}
