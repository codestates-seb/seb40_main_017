package team017.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(length = 45, nullable = false)
	private String name;

	@Column(length = 45, nullable = false, unique = true)
	private String email;

	@Column(length = 45, nullable = false)
	private String password;

	@Column(length = 45, nullable = false)
	private String phone;

	@Column(length = 45, nullable = false)
	private String address;

	/* Authority 로 일일히 확인하기 어려우니 컬럼 추가 */
	@Column
	private String role;

	/* security 이용하여 역할 추가 */
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	/* 💜 소비자 - 회원 일대일 연관 관계 : 회원 참조*/
	@OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Client client;

	/* 💜소비자 - 회원 연관 관계 편의 메서드 */
	public void setClient(Client client) {
		this.client = client;

		if (client.getMember() != this) {
			client.setMember(this);
		}
	}

	/* 🌸판매자 - 회원 일대일 연관 관계 : 회원 참조 */
	@OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Seller seller;

	/* 🌸판매자 - 회원 연관 관계 편의 메서드 */
	public void setSeller(Seller seller) {
		this.seller = seller;

		if (seller.getMember() != this) {
			seller.setMember(this);
		}
	}
}
