package team017.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/* 결제 요청할 때 사용 */
@Getter
@Setter
@ToString
public class ReadyResponse {
	private String tid;
	private String next_redirect_pc_url;
	private String partner_order_id;
}
