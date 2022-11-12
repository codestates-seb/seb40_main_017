package team017.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerId;

	/* 역할은 아마 CustomAuthorityUtil 만들어서 사용하지 않을까 */

	@Column
	private String introduce;

	/* 🌸 판매자 - 회원 일대일 연관 관계 : 회원 참조 */
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	/* 🌸판매자 - 회원 연관 관계 편의 메서드 */
	public void setMember(Member member) {
		this.member = member;

		if (member.getSeller() != this) {
			member.setSeller(this);
		}
	}

	/* 🌼판매자 - 판매자 이미지 일대일 연관 관계 : 판매자 참조 */
	@OneToOne(mappedBy = "seller", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private SellerImage sellerImage;

	/* 🌼판매자 - 이미지 연관 관계 편의 메서드*/
	public void setSellerImage(SellerImage sellerImage) {
		this.sellerImage = sellerImage;

		if (sellerImage.getSeller() != this) {
			sellerImage.setSeller(this);
		}
	}

	/* 🌺판매자 - 판매 내역 일대다 연관 관계 : 판매자 참조 */
	@OneToMany(mappedBy = "seller")
	private List<SellHistory> sellHistoryList = new ArrayList<>();

	/* 🌺판매자 - 판매 내역 연관 관계 편의 메서드 */
	public void addSellHistory(SellHistory sellHistory) {
		sellHistoryList.add(sellHistory);

		if (sellHistory.getSeller() != this) {
			sellHistory.setSeller(this);
		}
	}

	@Builder
	public Seller(Long sellerId, String introduce) {
		this.sellerId = sellerId;
		this.introduce = introduce;
	}
}
