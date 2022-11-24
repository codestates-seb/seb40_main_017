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
public class BoardPostDto {

    private Long sellerId;

    private String mainImage;       //게시판 썸네일 이미지

    @NotBlank
    private String title;           //게시글 제목

    @NotBlank
    private String content;         //게시글 내용

    @NotBlank
    private int price;              //상품 가격

    @NotBlank
    private int stock;              //상품 재고

    @NotBlank
    private int category;           //상품분류


}
