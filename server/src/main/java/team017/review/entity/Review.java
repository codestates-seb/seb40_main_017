package team017.review.entity;

import lombok.Builder;
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

    @Column
    private String image;

    @Column(nullable = false)
    private Double star;

    @Builder
    public Review(Long reviewId, String context, String image, Double star) {
        this.reviewId = reviewId;
        this.context = context;
        this.image = image;
        this.star = star;
    }
}
