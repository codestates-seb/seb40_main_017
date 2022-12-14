package team017.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerPatchDto {
	private String name;
	private long sellerId;
	private String phone;
	private String password;
	private String address;
	private String introduce;
	private String imageUrl;
}
