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
import lombok.Setter;
import team017.board.Entity.Board;
import team017.ord.entity.Ord;
import team017.product.Entity.Product;

@Getter
@Entity
@Setter
@NoArgsConstructor
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerId;

	@Column
	private String introduce;

	@Column
	private String imageUrl;

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

	@Builder
	public Seller(Long sellerId, String introduce, String imageUrl) {
		this.sellerId = sellerId;
		this.introduce = introduce;
		this.imageUrl = imageUrl;
	}

	/* 🧡게시판 - 판매자 일대다 연관 관계 : 판매자 참조 */
	@OneToMany(mappedBy = "seller", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Board.class)
	private List<Board> boardList = new ArrayList<>();

	/* 🧡게시판 - 판매자 연관 관계 편의 메서드*/
	public void addBoards (Board board) {
		boardList.add(board);

		if (board.getSeller() != this) {
			board.setSeller(this);
		}
	}

	/* 💙상품 - 판매자 일대다 연관 관계 : 판매자 참조 */
	@OneToMany(mappedBy = "seller",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Product.class)
	private List<Product> productList = new ArrayList<>();

	/* 💙상품 - 판매자 연관 관계 편의 메서드*/
	public void addProduct(Product product){
		productList.add(product);

		if(product.getSeller() !=this){
			product.setSeller(this);
		}
	}
}
