package team017.board.Dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardForSellerMyPageDto {

    private String title;

    private LocalDateTime createdAt;

    private int stock;

    private int soldStock; //팔린 갯수
}
