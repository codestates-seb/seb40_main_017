package team017.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.*;
import team017.comments.entity.Comment;

@Getter
@Entity
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberId;

	@Column(length = 45, nullable = false)
	private String name;

	@Column(length = 45, nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(length = 45, nullable = false)
	private String phone;

	@Column(length = 45, nullable = false)
	private String address;

	/* 소셜 로그인을 추가하면서 해당 판별을 위한 프로바이더 타입 추가 */
	@Enumerated(EnumType.STRING)
	private ProviderType providerType;

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

	public Member(String name, String email, //String password,
		ProviderType providerType, String role, List<String> roles) {
		this.name = name;
		this.email = email;
		//this.password = password;
		this.providerType = providerType;
		this.role = role;
		this.roles = roles;
	}
}