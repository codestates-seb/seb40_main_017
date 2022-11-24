package team017.board.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardTotalResponseDto {
    private Long boardId;

    private Long productId;

    private Long sellerId;

    private String sellerImage;

    private String mainImage;

    private String name;

    private String title;

    private int price;

    private int soldStock;

    private int category; //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)

    private double reviewAvg ; //별점의 평균

    private int reviewNum; //리뷰 갯수

}
