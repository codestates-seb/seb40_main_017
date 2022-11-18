package team017.image.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.board.Entity.Board;
import team017.global.audit.Auditable;
import team017.product.Entity.Product;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column
    private String contentImage;

    /* 🍓상품 이미지 - 상품 일대다 연관 관계 : 상품 참조 */
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

}
