package team017.board.Dto;
import lombok.*;
import team017.product.Entity.Product;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponseDto {

    private Long boardId;

    private Long productId;

    private Long sellerId;

    private String mainImage;               //게시판 썸네일 이미지

    private String name;                    //판매자 이름

    private String title;                   //게시글 제목

    private String content;                 //게시글 내용

    private int price;                      //상품 가격

    private int stock;                      //상품 재고

    private int category;                   //상품분류

    private Product.ProductStatus status;   //판매 상태

    private double reviewAvg ;              //별점의 평균

    private int leftStock;                  //잔여 재고

    private LocalDate createdAt;        //게시글 생성 시간

    private LocalDate modifiedAt;       //게시글 수정 시간

}
