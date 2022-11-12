package team017.board.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.global.audit.Auditable;
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
    private Long sellBoardId;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false )
    private int viewCount;

//    @Column
//    List<String> imageList = new ArrayList<String>();

    @Column(nullable = false)
    private double avg ;

    @OneToOne
    private Product product;

}
