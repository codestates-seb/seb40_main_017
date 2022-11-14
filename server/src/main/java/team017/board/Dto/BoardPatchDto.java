package team017.board.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardPatchDto {

    @NotBlank
    private Long boardId;

    private String title;

    private String content;

    private int price;

    private String status;

    private int category;

    private Long sellPhotoId;

}
