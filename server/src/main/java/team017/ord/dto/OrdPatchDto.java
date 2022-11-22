package team017.ord.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrdPatchDto {

    private Long clientId;

    private String address;

    private String phone;
}
