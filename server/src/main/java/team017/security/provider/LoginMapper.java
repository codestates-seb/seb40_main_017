package team017.security.provider;

import org.mapstruct.Mapper;

import team017.member.entity.Member;
import team017.security.dto.LoginResponse;
import team017.security.dto.TokenDto;

@Mapper(componentModel = "spring")
public interface LoginMapper {
	default LoginResponse loginResponseDto(Member member, TokenDto tokenDto) {
		if (tokenDto == null && member == null) {
			return null;
		}

		LoginResponse response =
			LoginResponse.builder()
				.memberId(member.getMemberId())
				.authorization(tokenDto.getAccessToken())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}
}
