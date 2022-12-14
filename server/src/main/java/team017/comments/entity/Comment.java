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

    /* π νμ - λκΈ λ€λμΌ μ°κ΄ κ΄κ³ : μλΉμ μ°Έμ‘° */
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    @ToString.Exclude
    private Member member;

    /* πκ²μν - λκΈ λ€λμΌ μ°κ΄ κ΄κ³ : νλ§€μ μ°Έμ‘° */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @ToString.Exclude
    private Board board;
}
