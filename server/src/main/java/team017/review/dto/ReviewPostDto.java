package team017.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ReviewPostDto {

    @NotNull
    private Long clientId;

    private Long boardId;

    @NotNull
    @Length(min = 8, max = 65535, message = "리뷰는 최소 8자를 입력하여야 합니다.")
    private String context;

    private String image;

    @NotNull
    private int star;
}
