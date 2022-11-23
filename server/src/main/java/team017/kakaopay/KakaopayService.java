package team017.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.member.entity.Member;
import team017.member.service.MemberService;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.service.OrdService;
import team017.security.utils.SecurityUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaopayService {

	private final MemberService memberService;

	@Value("${spring.security.oauth2.client.registration.kakao.adminKey}")
	private String adminKey;

	/* 결제 준비 */
	public ReadyResponse payReady(Ord ord) {
		log.info("# 카카오 결제 준비 서비스 접근");
		// String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findVerifiedMember(ord.getClient().getMember().getMemberId());

		/* 주문 불러오기 */
		if(member.getClient().getClientId() != ord.getClient().getClientId()) {
			throw new RuntimeException("잘못된 유저입니다.");
		}

		/* 카카오가 요구한 결제 요청? */
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("cid", "TC0ONETIME");  /* 테스트 용 가맹점 코드 */
		param.add("partner_order_id", ord.getOrdId()); /* 가맹점 주문번호 -> String 형 */
		param.add("partner_user_id", "17farm");
		param.add("item_name", ord.getProduct().getBoard().getTitle());
		param.add("quantity", ord.getTotalQuantity()); /* 개수는 상수형 */
		param.add("total_amount", ord.getTotalPrice()); /* 상품 총액 */
		param.add("tax_free_amount", 0); /* 상품 비과세 금액 */
		param.add("approval_url", "http://localhost:8080/order/pay/completed"); /* 결제 승인 url */
		param.add("cancel_url", "http://localhost:8080/order/pay/cancel"); /* 결제 취소 url */
		param.add("fail_url", "http://localhost:8080/order/pay/fail"); /* 결제 실패 url */

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(param, this.getHeaders());

		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";

		/* 이 부분에서 계속 바디가 생성되지 않는다고 나온다. */
		ReadyResponse readyResponse = template.postForObject(url, requestEntity, ReadyResponse.class);
		return readyResponse;
	}

	/* 승인 요청 메서드 */
	public ApproveResponse payApprove(String tid, String pgToken, Ord ord) {
		String email = SecurityUtil.getCurrentEmail();
		Member member = memberService.findMemberByEmail(email);
		log.info("현재 유저 이름 : {}", member.getName());

		/* 주문 명 만들기? */
		String ordName = ord.getOrdId() +"번째 " + ord.getProduct().getBoard().getTitle();

		/* 요청 값 */
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
		param.add("cid", "TC0ONETIME");
		param.add("tid", tid);
		param.add("partner_order_id", ordName);
		param.add("partner_user_id", "17farm");
		param.add("pg_token", pgToken);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(param, this.getHeaders());

		/* 외부 url 통신 */
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/approve";
		ApproveResponse approveResponse = template.postForObject(url, requestEntity, ApproveResponse.class);

		return approveResponse;
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", adminKey);
		headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		return headers;
	}
}
