package team017.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private Long sellerImageId;

	@Column
	private int photoSize;

	@Column
	private String image;
}
