package team017.comments.entity;

import lombok.*;
import team017.board.Entity.Board;
import team017.global.audit.Auditable;
import team017.member.entity.Member;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String context;

    /* 💝 회원 - 댓글 다대일 연관 관계 : 소비자 참조 */
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @ToString.Exclude
    private Member member;

    /* 🍉게시판 - 댓글 다대일 연관 관계 : 판매자 참조 */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @ToString.Exclude
    private Board board;
}
