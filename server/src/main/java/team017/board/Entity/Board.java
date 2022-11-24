package team017.board.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Column(nullable = false)
    private String content;

    @Column(nullable = false )
    private int reviewNum; //리뷰 갯수

    @Column(nullable = false)
    private int soldStock; //팔린 갯수

    @Column(nullable = false)
    private double reviewAvg ; //별점의 평균

    /* 🍋게시판 - 상품 일대일 연관 관계 : 상품 참조*/
    @OneToOne
    @JoinColumn(name = "productId" , referencedColumnName = "productId")
    private Product product;

    /* 🍋게시판 - 상품 연관 관계 편의 메서드 */
    public void setBoard(Product product) {
        this.product = product;

        if (product.getBoard() != this) {
            product.setBoard(this);
        }
    }

    /* 🧡게시판 - 판매자 다대일 연관 관계 : 판매자 참조 */
    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

    /* 🍉리뷰 - 판매자 일대다 연관 관계 : 판매자 참조 */
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Review> reviewList = new ArrayList<>();

    /* 🍉리뷰 - 판매자 연관 관계 편의 메서드 */
    public void addReview (Review review) {
        reviewList.add(review);

        if (review.getBoard() != this) {
            review.setBoard(this);
        }
    }
}
