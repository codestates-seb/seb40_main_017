package team017.ord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.ord.entity.Ord;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdSellerResponseDto { //판매 내역 조회

    private Long boardId;

    private Long sellerId;

    private String title;

    private String name;

    private int price;

    private String phone;

    private int stock;

    private int category;

    private int leftStock;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;
}