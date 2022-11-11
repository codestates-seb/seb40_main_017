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
public class BoardPostDto {

    private Long sellerId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private int price;

    @NotBlank
    private int stock;

    @NotBlank
    private int category;

    @Getter
    @AllArgsConstructor
    public static class ProductPostDto {


    }


}
