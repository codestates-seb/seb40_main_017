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

    private String sellerImage;     //판매자 이미지

    private String mainImage;       //게시판 썸네일 이미지

    private String name;            //판매자 이름

    private String title;           //게시글 제목

    private int price;              //상품 가격

    private int soldStock;          //잔여 재고

    private int category;           //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)

    private double reviewAvg ;      //별점의 평균

    private int reviewNum;          //리뷰 갯수

}
