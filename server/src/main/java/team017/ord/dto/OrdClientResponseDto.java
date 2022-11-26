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
public class OrdClientResponseDto { //구매 내역 조회

    private Long ordId;

    private Long clientId;

    private Long boardId;

    private String title;

    private String name;

    private String address;

    private String phone;

    private int quantity;
}
