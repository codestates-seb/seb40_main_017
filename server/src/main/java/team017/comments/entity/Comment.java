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

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @ToString.Exclude
    private Member member;

    @Column(nullable = false)
    private String context;

    private String commentMemberName;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @ToString.Exclude
    private Board board;
}
