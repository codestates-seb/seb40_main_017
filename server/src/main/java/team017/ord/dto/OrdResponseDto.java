package team017.ord.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.ord.entity.Ord;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrdResponseDto {
    private Long ordId;
    private Long clientId;
    private Long boardId;
    private String name;
    private String address;
    private String phone;
    private int totalPrice;
    private int totalQuantity;
    private Ord.OrdStatus ordStatus;
//    private LocalDateTime createdAt;
//    private LocalDateTime modifiedAt;
}

