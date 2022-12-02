package team017.ord.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrdPostDto {

    private Long clientId;

    private Long boardId;

    private String address;

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "잘못된 형식의 휴대전화 번호 입니다.")
    private String phone;

    private int quantity;

    private int totalPrice;

}
