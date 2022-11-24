package team017.board.Dto;
import lombok.*;
import team017.product.Entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;

    private Long productId;

    private Long sellerId;

    private String name;                    //판매자 이름

    private String title;                   //게시글 제목

    private String content;                 //게시글 내용

    private int price;                      //상품 가격

    private int stock;                      //상품 재고

    private int category;                   //상품분류

    private Product.ProductStatus status;   //판매 상태

    private double reviewAvg ;              //별점의 평균

    private int soldStock;                  //팔린 갯수

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
