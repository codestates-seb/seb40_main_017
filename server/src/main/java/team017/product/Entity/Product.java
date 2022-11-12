package team017.product.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import team017.board.Entity.Board;
import team017.member.entity.Seller;


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

    @ColumnDefault(value ="'selling'")
    private String status; //판매 상태

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int category; //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)


    @OneToOne(mappedBy = "product",cascade = {CascadeType.REMOVE}, targetEntity = Board.class )
    private Board board;

    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

}
