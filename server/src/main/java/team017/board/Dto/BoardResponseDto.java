package team017.board.Dto;
import lombok.*;

import java.time.LocalDateTime;
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

    private Long sellPhotoId;

    private String status; //판매 상태

    private int view;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

}
