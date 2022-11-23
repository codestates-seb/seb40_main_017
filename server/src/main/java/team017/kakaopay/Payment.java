package team017.kakaopay;

import lombok.Builder;

@Builder
public class Payment {
	private String itemName;
	private String paymentMethodType;
	private String payCode;
}
