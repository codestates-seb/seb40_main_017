package team017.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerPatchDto {
	private long sellerId;
	private String name;
	private String phone;
	private String password;
	private String address;
	private String introduce;
}
