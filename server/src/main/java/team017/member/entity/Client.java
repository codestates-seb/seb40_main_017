package team017.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Builder
	public Client(Long clientId) {
		this.clientId = clientId;
	}
}
