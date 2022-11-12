package team017.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import team017.member.dto.MemberDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	Member memberDtoToMember(MemberDto.Post post);

	MemberDto.Response memberToMemberResponseDto(Member member);

	MemberDto.ClientDto memberToClientDto(Member member, Client client);

	MemberDto.SellerDto memberToSellerDto(Member member, Seller seller);
}
