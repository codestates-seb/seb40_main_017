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

	@Column
	private String tid;

	/* ๐ ์๋น์ - ์ฃผ๋ฌธ ๋ค๋์ผ ์ฐ๊ด ๊ด๊ณ : ์๋น์ ์ฐธ์กฐ */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	/* ๐์ํ - ์ฃผ๋ฌธ ์ผ๋์ผ ์ฐ๊ด ๊ด๊ณ : ์ํ ์ฐธ์กฐ */
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column
	@Enumerated(EnumType.STRING)
	private OrdStatus status = OrdStatus.ORD_REQUEST;

	public enum OrdStatus {

		ORD_REQUEST(1, "์ฃผ๋ฌธ ์์ฒญ"),
		PAY_COMPLETE(2, "๊ฒฐ์  ์๋ฃ");

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
