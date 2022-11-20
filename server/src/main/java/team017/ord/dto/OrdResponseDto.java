package team017.ord.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team017.ord.entity.Ord;

@Getter
@Setter
@NoArgsConstructor
public class OrdResponseDto {
    private Long ordId;

    private Long clientId;

    private Long boardId;

    private Ord.OrdStatus ordStatus;

    private String address;

    private String phone;
}
