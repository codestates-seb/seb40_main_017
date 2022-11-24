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

    private String name;

    private String title;

    private String content;

    private int price;

    private int stock;

    private int category; //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)

    private Product.ProductStatus status; //판매 상태

    private int view;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private double reviewAvg ; //별점의 평균

    private int soldStock; //팔린 갯수

    private String mainImage;

}
