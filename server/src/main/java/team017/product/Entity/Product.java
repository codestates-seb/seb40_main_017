package team017.product.Entity;

import java.util.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.board.Entity.Board;
import team017.member.entity.Seller;
import team017.ord.entity.Ord;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private int price;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.PRD_SELLING;

    @Column(nullable = false)
    @PositiveOrZero
    @Max(50)
    private int stock;

    @Column(nullable = false)
    @PositiveOrZero
    @Max(50)
    private int leftStock;

    @Column(nullable = false)
    private int category;

    @Column
    private String mainImage;

    /* πκ²μν - μν μΌλμΌ μ°κ΄ κ΄κ³ : μν μ°Έμ‘°*/
    @OneToOne(mappedBy = "product",cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, targetEntity = Board.class )
    private Board board;

    /* πκ²μν - μν μ°κ΄ κ΄κ³ λ©μλ */
    public void setProduct(Board board) {
        this.board = board;

        if (board.getProduct() != this) {
            board.setProduct(this);
        }
    }

    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

    /* πμν - μ£Όλ¬Έ μΌλμΌ μ°κ΄ κ΄κ³ : μν μ°Έμ‘° */
    @OneToMany(mappedBy = "product", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Ord> ordList = new ArrayList<>();

    public void addOrd(Ord ord) {
        ordList.add(ord);

        if (ord.getProduct() != this) {
            ord.setProduct(this);
        }
    }

    public enum ProductStatus{
        PRD_SELLING("1", "νλ§€μ€"),
        PRD_SOLDOUT("2", "λ§€μ§");

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

}
