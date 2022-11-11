package team017.member.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import team017.member.dto.MemberDto;
import team017.member.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	Member memberDtoToMember(MemberDto.Post post);

	MemberDto.Response memberToMemberResponseDto(Member member);

	List<MemberDto.Response> memberToMemberResponseDtos(List<Member> members);
}
