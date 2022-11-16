package team017.board.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.product.Entity.Product;

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

    private Product.ProductStatus status;

    private int category;

    private Long sellPhotoId;

}
