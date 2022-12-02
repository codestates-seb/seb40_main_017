package team017.review.entity;

import lombok.*;
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

//    @Column
//    private String image;

    @Column(nullable = false)
    private int star;

    /* 💝 소비자 - 리뷰 다대일 연관 관계 : 소비자 참조 */
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    /* 🍉게시판 - 리뷰 다대일 연관 관계 : 게시판 참조 */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @ToString.Exclude
    private Board board;

}
