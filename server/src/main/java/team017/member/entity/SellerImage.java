package team017.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team017.global.audit.Auditable;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerImage extends Auditable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerPhotoId;

	@Column
	private int photoSize;

	@Column
	private String image;

	/* 🌼판매자 - 판매자 이미지 일대일 연관 관계 : 판매자 참조 */
	@OneToOne
	@JoinColumn(name = "seller_id")
	private Seller seller;

	/* 🌼판매자 - 이미지 연관 관계 편의 메서드*/
	public void setSeller(Seller seller) {
		this.seller = seller;

		if (seller.getSellerImage() != this) {
			seller.setSellerImage(this);
		}
	}
}
