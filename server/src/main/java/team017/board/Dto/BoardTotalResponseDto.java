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

    private String name;

    private String title;

    private int price;

    private int stock;

    private int category; //상품분류 (과일 :1 , 채소: 2, 곡물: 3,  견과류: 4)

    private double avg ; //별점의 평균

}
