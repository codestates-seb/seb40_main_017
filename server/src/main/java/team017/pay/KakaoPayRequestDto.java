package team017.pay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayRequestDto {
    private String tid; // 결제 고유 번호
    private String next_redirect_pc_url; // 요청한 클라이언트가 PC 웹일 경우 카카오톡으로 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL
    private String partner_order_id; //가맹점 주문 번호
}
