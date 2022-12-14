package team017.board.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.comments.entity.Comment;
import team017.global.audit.Auditable;
import team017.member.entity.Seller;
import team017.product.Entity.Product;
import team017.review.entity.Review;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "board")
public class Board extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false )
    private int reviewNum;

    @Column(nullable = false)
    private double reviewAvg ;

    /* πκ²μν - μν μΌλμΌ μ°κ΄ κ΄κ³ : μν μ°Έμ‘°*/
    @OneToOne
    @JoinColumn(name = "productId" , referencedColumnName = "productId")
    private Product product;

    /* πκ²μν - μν μ°κ΄ κ΄κ³ νΈμ λ©μλ */
    public void setBoard(Product product) {
        this.product = product;

        if (product.getBoard() != this) {
            product.setBoard(this);
        }
    }

    /* π§‘κ²μν - νλ§€μ λ€λμΌ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */
    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

    /* πλ¦¬λ·° - νλ§€μ μΌλλ€ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */

    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Review> reviewList = new ArrayList<>();

    /* πνλ§€ λκΈ - νλ§€μ μΌλλ€ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();

    /* πλ¦¬λ·° - νλ§€μ μ°κ΄ κ΄κ³ νΈμ λ©μλ */
    public void addReview (Review review) {
        reviewList.add(review);

        if (review.getBoard() != this) {
            review.setBoard(this);
        }
    }
}
