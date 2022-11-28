package team017.member.mapper;

import org.mapstruct.Mapper;

import team017.member.dto.ClientPatchDto;
import team017.member.dto.MemberDto;
import team017.member.dto.SellerPatchDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.security.jwt.dto.LoginResponse;
import team017.security.jwt.dto.TokenDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	/* 등록 */
	Member memberDtoToMember(MemberDto.Post post);

	/* 응답 */
	default MemberDto.SelleResponseDto memberToSellerDto(Member member) {
		if (member == null) {
			return null;
		}

		MemberDto.SelleResponseDto response =
			MemberDto.SelleResponseDto.builder()
				.memberId(member.getMemberId())
				.sellerId(member.getSeller().getSellerId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.address(member.getAddress())
				.role(member.getRole())
				.introduce(member.getSeller().getIntroduce())
				.imageUrl(member.getSeller().getImageUrl())
				.build();

		return response;
	}

	default MemberDto.ClientResponseDto memberToClientDto(Member member) {
		if (member == null) {
			return null;
		}
		MemberDto.ClientResponseDto response =
			MemberDto.ClientResponseDto.builder()
				.memberId(member.getMemberId())
				.clientId(member.getClient().getClientId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.address(member.getAddress())
				.role(member.getRole())
				.build();

		return response;
	}

	/* 수정 */
	Member sellerPatchDtoToMember(SellerPatchDto sellerPatchDto);
	Member clientPatchDtoToMember(ClientPatchDto clientPatchDto);
	Seller sellerPatchDtoToSeller(SellerPatchDto sellerPatchDto);
	Client clientPatchDtoToClient(ClientPatchDto clientPatchDto);

	/* 새로 고침 */
	default LoginResponse.Cilent getClientResponse(Member member) {
		if (member == null && member.getClient() == null) {
			return null;
		}
		LoginResponse.Cilent response =
			LoginResponse.Cilent.builder()
				.memberId(member.getMemberId())
				.clientId(member.getClient().getClientId())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}
	default LoginResponse.Seller getSellerResponse(Member member) {
		if (member == null && member.getSeller() == null) {
			return null;
		}
		LoginResponse.Seller response =
			LoginResponse.Seller.builder()
				.memberId(member.getMemberId())
				.sellerId(member.getSeller().getSellerId())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}

	/* 소셜 용 --> 소셜은 권한이 없기 때문 */
	default LoginResponse.Member socialLoginResponseDto(Member member, String token) {
		LoginResponse.Member response =
			LoginResponse.Member.builder()
				.memberId(member.getMemberId())
				.name(member.getName())
				.role(member.getRole())
				/* 테스트 중에는 화면에 띄워야 확인이 편해서 바디에 넣고 있음. */
				.authorization(token)
				.build();

		return response;
	}
}
