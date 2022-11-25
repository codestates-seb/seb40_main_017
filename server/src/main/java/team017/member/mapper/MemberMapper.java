package team017.member.mapper;

import org.mapstruct.Mapper;

import team017.member.dto.ClientPatchDto;
import team017.member.dto.MemberDto;
import team017.member.dto.SellerPatchDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.security.dto.TokenDto;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	/* 등록 */
	Member memberDtoToMember(MemberDto.Post post);

	/* 응답 */
	MemberDto.ClientResponseDto memberToClientDto(Member member, Client client);
	MemberDto.SelleResponseDto memberToSellerDto(Member member, Seller seller);
	default MemberDto.SellerLoginResponse memberToSellerDto(Member member, TokenDto tokenDto) {
		if (member == null && tokenDto == null) {
			return null;
		}

		MemberDto.SellerLoginResponse response =
			MemberDto.SellerLoginResponse.builder()
				.memberId(member.getMemberId())
				.sellerId(member.getSeller().getSellerId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.address(member.getAddress())
				.role(member.getRole())
				.introduce(member.getSeller().getIntroduce())
				.imageUrl(member.getSeller().getImageUrl())
				.authorization(tokenDto.getAccessToken())
				.build();

		return response;
	}

	default MemberDto.ClientLoginResponse memberToClientDto(Member member, TokenDto tokenDto) {
		if (member == null && tokenDto == null) {
			return null;
		}
		MemberDto.ClientLoginResponse response =
			MemberDto.ClientLoginResponse.builder()
				.memberId(member.getMemberId())
				.clientId(member.getClient().getClientId())
				.email(member.getEmail())
				.name(member.getName())
				.phone(member.getPhone())
				.address(member.getAddress())
				.role(member.getRole())
				.authorization(tokenDto.getAccessToken())
				.build();

		return response;
	}

	/* 수정 */
	Member sellerPatchDtoToMember(SellerPatchDto sellerPatchDto);
	Member clientPatchDtoToMember(ClientPatchDto clientPatchDto);
	Seller sellerPatchDtoToSeller(SellerPatchDto sellerPatchDto);
	Client clientPatchDtoToClient(ClientPatchDto clientPatchDto);
}
