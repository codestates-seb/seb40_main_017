package team017.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/* 결재 내역을 가져올 때 사용 */
@Getter
@Setter
@ToString
public class Amount {
	private int total; /* 전체 금액 */
	private int tax_free; /* 비과세 금액 */
	private int vat; /* 부과세 금액 */
	private int point; /* 사용한 포인트 */
	private int discount; /* 할인 금액 */
}
