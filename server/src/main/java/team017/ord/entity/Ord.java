package team017.ord.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ordId;

	@Column(length = 50, nullable = false)
	private String address;

	@Column(length = 13, nullable = false, unique = true)
	private String phone;

	// @Enumerated(value = EnumType.STRING)
	// @Column()
	// private String status;

	//상품 PK OneToMany
	//  @OneToMany(mappedBy = "ord", targetEntity = product.class)

	//소비자 PK OneToMany
	//   @OneToMany(mappedBy = "ord", targetEntity = client.class)

}
