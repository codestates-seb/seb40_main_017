package team017.ord.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.global.audit.Auditable;
import team017.member.entity.Client;
import team017.product.Entity.Product;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ord extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordId;

	@Column(length = 50, nullable = false)
	private String address;

	@Column(length = 13, nullable = false)
	private String phone;

	@Column
	private int quantity;

	@Column
	private int totalPrice;

	/* 💛 소비자 - 주문 다대일 연관 관계 : 소비자 참조 */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	/* 🍑상품 - 주문 일대일 연관 관계 : 상품 참조 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	@Enumerated(EnumType.STRING)
	private OrdStatus status = OrdStatus.ORD_REQUEST;

	public enum OrdStatus {
		ORD_REQUEST(1, "주문 요청"),
		PAY_COMPLETE(2, "결제 완료");

		@Getter
		private int Number;
		@Getter
		private String Description;

		OrdStatus(int number, String description) {
			Number = number;
			Description = description;
		}
	}
}
