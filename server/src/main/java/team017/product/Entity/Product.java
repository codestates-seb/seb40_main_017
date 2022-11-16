package team017.product.Entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import team017.board.Entity.Board;
import team017.member.entity.Seller;
import team017.ord.entity.Ord;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DynamicInsert
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private int price;

    @ColumnDefault(value ="'PRD_SELLING'")
    @Enumerated(EnumType.STRING)
    private ProductStatus status; //판매 상태

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int category;


    /* 🍋게시판 - 상품 일대일 연관 관계 : 상품 참조*/
    @OneToOne(mappedBy = "product",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Board.class )
    private Board board;

    /* 🍋게시판 - 상품 연관 관계 메서드 */
    public void setProduct(Board board) {
        this.board = board;

        if (board.getProduct() != this) {
            board.setProduct(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

    /* 🍑상품 - 주문 일대다 연관 관계 : 상품 참조 */
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Ord> ordList = new ArrayList<>();

    /* 🍑상품 - 주문 연관 관계 편의 메서드 */
    public void addOrd(Ord ord) {
        ordList.add(ord);

        if (ord.getProduct() != this) {
            ord.setProduct(this);
        }
    }

    public enum ProductStatus{
        PRD_SELLING("1", "판매중"),
        PRD_SOLDOUT("2", "매진");

        private String code;
        private String value;

        ProductStatus(String value, String code) {
            this.code =code;
            this.value = value;
        }

        public String getCode(){
            return code;
        }
        public String getValue(){
            return value;
        }

    }

//    public enum CategoryType{
//        FRUITS("1", "과일"),
//        VEGETABLES("2", "채소"),
//        GRANINS("3" , "곡물"),
//        NUTS("4", "견과류");
//
//        private String code;
//        private String value;
//
//        CategoryType(String value, String code) {
//            this.code =code;
//            this.value = value;
//        }
//
//        public String getCode(){
//            return code;
//        }
//        public String getValue(){
//            return value;
//        }
//
//    }

}
