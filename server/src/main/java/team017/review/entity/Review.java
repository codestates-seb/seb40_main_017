package team017.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import team017.global.audit.Auditable;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private String context;

    @Column(length = 255)
    private String image;

    @Column(nullable = false)
    private Double star;

}
