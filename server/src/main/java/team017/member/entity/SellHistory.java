package team017.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team017.global.audit.Auditable;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SellHistory extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellHistoryId;

	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	@Column
	private int productNum;

	@Column
	private int totalPrice;

	/* 판매자 - 판매 내역 연관 관계 편의 메서드*/
	public void setSeller(Seller seller) {
		this.seller = seller;

		// if (!seller.getSellHistoryList().contains(this)) {
		// 	seller.getSellHistoryList().add(this);
		// }
	}
}
