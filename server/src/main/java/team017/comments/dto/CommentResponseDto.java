package team017.comments.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;

    private Long boardId;

    private Long memberId;

    private String name;

    private String context;

    private LocalDate createdAt;

    private LocalDate modifiedAt;

}
