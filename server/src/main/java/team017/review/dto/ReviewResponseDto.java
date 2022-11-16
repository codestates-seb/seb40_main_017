package team017.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDto {
    private Long clientId;

    private Long boardId;

    private Long reviewId;

    private String name;

    private String context;

    private String image;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
