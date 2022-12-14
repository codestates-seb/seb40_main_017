package team017.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    private int star;

    private LocalDate createdAt;

    private LocalDate modifiedAt;

}
