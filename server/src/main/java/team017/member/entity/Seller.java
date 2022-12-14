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

	/* πΈ νλ§€μ - νμ μΌλμΌ μ°κ΄ κ΄κ³ : νμ μ°Έμ‘° */
	@OneToOne
	@JoinColumn(name = "member_id")
	private Member member;

	/* πΈνλ§€μ - νμ μ°κ΄ κ΄κ³ νΈμ λ©μλ */
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

	/* π§‘κ²μν - νλ§€μ μΌλλ€ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */
	@OneToMany(mappedBy = "seller", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Board.class)
	private List<Board> boardList = new ArrayList<>();

	/* π§‘κ²μν - νλ§€μ μ°κ΄ κ΄κ³ νΈμ λ©μλ*/
	public void addBoards (Board board) {
		boardList.add(board);

		if (board.getSeller() != this) {
			board.setSeller(this);
		}
	}

	/* πμν - νλ§€μ μΌλλ€ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */
	@OneToMany(mappedBy = "seller",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Product.class)
	private List<Product> productList = new ArrayList<>();

	/* πμν - νλ§€μ μ°κ΄ κ΄κ³ νΈμ λ©μλ*/
	public void addProduct(Product product){
		productList.add(product);

		if(product.getSeller() !=this){
			product.setSeller(this);
		}
	}
}
