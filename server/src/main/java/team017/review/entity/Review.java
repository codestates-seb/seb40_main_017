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

    /* ๐ ์๋น์ - ๋ฆฌ๋ทฐ ๋ค๋์ผ ์ฐ๊ด ๊ด๊ณ : ์๋น์ ์ฐธ์กฐ */
    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    /* ๐๊ฒ์ํ - ๋ฆฌ๋ทฐ ๋ค๋์ผ ์ฐ๊ด ๊ด๊ณ : ๊ฒ์ํ ์ฐธ์กฐ */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @ToString.Exclude
    private Board board;

}
