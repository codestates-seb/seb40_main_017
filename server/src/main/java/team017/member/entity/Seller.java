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
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerId;

	/* 역할은 아마 CustomAuthorityUtil 만들어서 사용하지 않을까 */

	@Column
	private String introduce;

	@Builder
	public Seller(Long sellerId, String introduce) {
		this.sellerId = sellerId;
		this.introduce = introduce;
	}
}
