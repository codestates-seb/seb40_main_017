package team017.board.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardForSellerMyPageDto {

    private String title;               //게시글 제목

    private int stock;                  //초기 상품 재고

    private int leftStock;              //잔여 재고

    private LocalDateTime createdAt;    //게시글 생성 시간
}
