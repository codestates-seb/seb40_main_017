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

    private String mainImage;               //게시판 썸네일 이미지

    private String title;                   //게시글 제목

    private String content;                 //게시글 내용

    private int price;                      //상품 가격

    private Product.ProductStatus status;   //상품 판매 상태

    private int category;                   //상품분류

}
