package team017.payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import team017.board.Entity.Board;
import team017.board.Repository.BoardRepository;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.repository.OrdRepository;
import team017.ord.service.OrdService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoPayService {

    private final OrdService ordService;
    private final OrdRepository ordRepository;
    private final OrdMapper ordMapper;
    private final BoardRepository boardRepository;
    @Value("${spring.security.oauth2.client.registration.kakao.adminKey}")
    private String adminKey;

    // 결제 준비 메서드
    public ReadyResponseDto payReady(Ord ord) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();

        parameters.add("cid", "TC0ONETIME");                                            //가맹점 코드, 10자
        parameters.add("partner_order_id", Long.toString(ord.getOrdId()));              //가맹점 주문번호, 최대 100자
        parameters.add("partner_user_id", "17farm");                                    //가맹점 회원 id, 최대 100자
        parameters.add("item_name", ord.getProduct().getBoard().getTitle());            //상품명
        parameters.add("quantity", Integer.toString(ord.getQuantity()));                //상품 수량
        parameters.add("total_amount", Integer.toString(ord.getTotalPrice()));          //상품 총액
        parameters.add("tax_free_amount", "0");                                         //상품 총액

        parameters.add("approval_url", "https://www.17farm-server.shop/order/pay/completed/"+ ord.getOrdId());    // 결제 승인 시 넘어갈 redirect url,
        parameters.add("cancel_url", "https://www.17farm-server.shop/order/pay/cancel/"+ ord.getOrdId());         // 결제 취소 시 넘어갈 redirect url,
        parameters.add("fail_url", "https://www.17farm-server.shop/order/pay/fail/"+ ord.getOrdId());             // 결제 실패 시 넘어갈 redirect url,

        //Header + Body 합치기
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부 url 요청 통로 열기
        RestTemplate template = new RestTemplate();
        template.setRequestFactory(new HttpComponentsClientHttpRequestFactory()); //에러 메시지 확인

        String url = "https://kapi.kakao.com/v1/payment/ready";

        ReadyResponseDto readyResponseDto = template.postForObject(url, requestEntity, ReadyResponseDto.class);
        ord.setTid(readyResponseDto.getTid());
        ordRepository.save(ord);

        log.info("결제준비 응답객체: " + readyResponseDto);

        return readyResponseDto;
    }

    // 결제 승인요청 메서드
    public OrdResponseDto payApprove(Long ordId, String pgToken) {

        Ord findOrd = ordService.findVerifiedOrd(ordId);
        log.info("partner_order_id:"+ ordId);
        log.info("tid:"+ findOrd.getTid());

        // request
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
        parameters.add("cid", "TC0ONETIME");                        //가맹점 코드
        parameters.add("tid", findOrd.getTid());                    //결제 고유번호, 결제 준비 API 응답에 포함
        parameters.add("partner_order_id", Long.toString(ordId));   //가맹점 주문번호, 결제 준비 API 요청과 일치해야 함
        parameters.add("partner_user_id", "17farm");                //가맹점 회원 id, 결제 준비 API 요청과 일치해야 함
        parameters.add("pg_token", pgToken);                        //결제승인 요청을 인증하는 토큰

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부 url 통신
        RestTemplate template = new RestTemplate();

        String url = "https://kapi.kakao.com/v1/payment/approve";

        ApproveResponseDto approveResponse = template.postForObject(url, requestEntity, ApproveResponseDto.class);
        log.info("결재승인 응답객체: " + approveResponse);

        //order 상태 PAY_COMPLETE 로 변경
         findOrd.setStatus(Ord.OrdStatus.PAY_COMPLETE);
         ordRepository.save(findOrd);

        return ordMapper.ordToOrdResponseDto(findOrd);
    }

    /* 결제 취소 혹은 삭제 */
    public void cancelOrFailPayment(Long ordId) {

        //결제 취소, 결제 실패로 인한 잔여 재고 수정
        Ord findOrd = ordService.findVerifiedOrd(ordId);
        Board findBoard = findOrd.getProduct().getBoard();

        findBoard.getProduct().setLeftStock(findBoard.getProduct().getLeftStock() + findOrd.getQuantity());
        boardRepository.save(findBoard);
        ordService.deleteOrd(ordId);
    }

    // 서버로 요청할 Header
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + adminKey);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return headers;
    }
}
