package team017.board.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.global.audit.Auditable;
import team017.member.entity.Seller;
import team017.product.Entity.Product;

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
    private int viewCount;

//    @Column(nullable = false )
//    private String status; //판매 상태

//    @Column
//    private List<String> imageList = new ArrayList<>();

    @Column(nullable = false)
    private double avg ;

    @OneToOne
    @JoinColumn(name = "productId" , referencedColumnName = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sellerId" , referencedColumnName = "sellerId")
    private Seller seller;

}
