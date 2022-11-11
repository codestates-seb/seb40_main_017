package team017.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
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

	@Builder
	public Member(Long memberId, String name, String email, String password, String phone, String address) {
		this.memberId = memberId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address = address;
	}
}
