package team017.ord.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdResponseDto {
    private Long ordId;

    private Long clientId;

    private Long boardId;

    private String address;

    private String phone;
}
