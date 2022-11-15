package team017.member.mapper;

import org.mapstruct.Mapper;

import team017.member.dto.ClientPatchDto;
import team017.member.dto.MemberDto;
import team017.member.dto.SellerPatchDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	/* 등록 */
	Member memberDtoToMember(MemberDto.Post post);

	/* 응답 */
	MemberDto.ClientResponseDto memberToClientDto(Member member, Client client);
	MemberDto.SelleResponseDto memberToSellerDto(Member member, Seller seller);

	/* 수정 */
	Member sellerPatchDtoToMember(SellerPatchDto sellerPatchDto);
	Member clientPatchDtoToMember(ClientPatchDto clientPatchDto);
	Seller sellerPatchDtoToSeller(SellerPatchDto sellerPatchDto);
	Client clientPatchDtoToClient(ClientPatchDto clientPatchDto);
}
