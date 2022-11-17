package team017.security.jwt;

import org.mapstruct.Mapper;

import team017.security.jwt.dto.LoginResponse;
import team017.security.jwt.dto.TokenDto;

@Mapper(componentModel = "spring")
public interface LoginMapper {
	default LoginResponse loginResponseDto(long memberId, TokenDto tokenDto) {
		if (tokenDto == null) {
			return null;
		}

		LoginResponse response =
			LoginResponse.builder()
				.memberId(memberId)
				.authorization(tokenDto.getAccessToken())
				.refresh(tokenDto.getRefreshToken())
				.build();

		return response;
	}
}
