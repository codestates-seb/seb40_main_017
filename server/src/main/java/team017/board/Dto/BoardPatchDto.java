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

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private int price;

    @NotBlank
    private String status;

    @NotBlank
    private int category;

    private Long sellPhotoId;

}
