package team017.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.board.Entity.Board;
import team017.global.audit.Auditable;
import team017.member.entity.Client;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private String context;

    @Column
    private String image;

    @Column(nullable = false)
    private int star;
    /* 💝 소비자 - 리뷰 다대일 연관 관계 : 소비자 참조 */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /* 🍉리뷰 - 판매자 다대일 연관 관계 : 판매자 참조 */
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Review(Long reviewId, String context, String image, int star) {
        this.reviewId = reviewId;
        this.context = context;
        this.image = image;
        this.star = star;
    }
}
