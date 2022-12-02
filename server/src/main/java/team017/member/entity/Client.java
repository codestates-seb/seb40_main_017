package team017.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import team017.ord.entity.Ord;
import team017.review.entity.Review;

@Getter
@Entity
@NoArgsConstructor
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clientId;

	/* 역할은 아마 CustomAuthorityUtil 로 부여하지 않을까 */

	/* 💜소비자 - 회원 일대일 연관 관계 : 회원 참조*/
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	/* 💜소비자 - 회원 연관 관계 편의 메서드 */
	public void setMember(Member member) {
		this.member = member;

		if (member.getClient() != this) {
			member.setClient(this);
		}
	}

	/* 💛 소비자 - 주문 일대다 연관 관계 : 소비자 참조 */
	@OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Ord> ordList = new ArrayList<>();

	/* 💛 소비자 - 주문 연관 관계 편의 메서드 */
	public void addOrd (Ord ord) {
		ordList.add(ord);

		if (ord.getClient() != this) {
			ord.setClient(this);
		}
	}

	/* 💝 소비자 - 리뷰 일대다 연관 관계 : 소비자 참조 */
	@OneToMany(mappedBy = "client", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private List<Review> reviewList = new ArrayList<>();

	/* 💝 소비자 - 리뷰 연관 관계 편의 메서드 */
	public void addReview (Review review) {
		reviewList.add(review);

		if (review.getClient() != this) {
			review.setClient(this);
		}
	}

	@Builder
	public Client(Long clientId) {
		this.clientId = clientId;
	}
}
