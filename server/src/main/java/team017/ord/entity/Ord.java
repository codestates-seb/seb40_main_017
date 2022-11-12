package team017.ord.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.member.entity.Client;
import team017.member.entity.Seller;
import team017.product.Entity.Product;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Ord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordId;

	@Column(length = 50, nullable = false)
	private String address;

	@Column(length = 13, nullable = false, unique = true)
	private String phone;

	// @Enumerated(value = EnumType.STRING)
	// @Column()
	// private String status;

	/* 💖판매자 - 주문 다대일 연관 관계 : 판매자 참조 */
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	/* 💛 소비자 - 주문 다대일 연관 관계 : 소비자 참조 */
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Client client;

	/* 🍑상품 - 주문 다대일 연관 관계 : 상품 참조 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
}
