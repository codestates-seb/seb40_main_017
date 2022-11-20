package team017.ord.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrdPostDto {


    private Long clientId;

    private Long boardId;

    private String address;

    private String phone;
}
