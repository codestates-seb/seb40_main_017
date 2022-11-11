package team017.product.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private String status; //판매 상태

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String category; //상품분류


}
