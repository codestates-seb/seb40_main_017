package team017.security.dto;

import javax.servlet.http.HttpServletRequest;

import org.mapstruct.Mapper;

import team017.member.entity.Member;
import team017.security.dto.LoginResponse;
import team017.security.dto.TokenDto;

@Mapper(componentModel = "spring")
public interface LoginMapper {

	/* 로그인 용 매퍼 */
	default LoginResponse.Cilent loginClientResponseDto(Member member, TokenDto tokenDto) {
		if (member == null && tokenDto == null) {
			return null;
		}
		LoginResponse.Cilent response =
			LoginResponse.Cilent.builder()
				.memberId(member.getMemberId())
				.clientId(member.getClient().getClientId())
				.authorization(tokenDto.getAccessToken())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}
	default LoginResponse.Seller loginSellerResponseDto(Member member, TokenDto tokenDto) {
		if (member == null && tokenDto == null) {
			return null;
		}
		LoginResponse.Seller response =
			LoginResponse.Seller.builder()
				.memberId(member.getMemberId())
				.sellerId(member.getSeller().getSellerId())
				.authorization(tokenDto.getAccessToken())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}

	/* 새로 고침 */
	default LoginResponse.Cilent getClientToken(Member member, String token) {
		if (member == null && token == null && member.getClient() == null) {
			return null;
		}
		LoginResponse.Cilent response =
			LoginResponse.Cilent.builder()
				.memberId(member.getMemberId())
				.clientId(member.getClient().getClientId())
				.authorization(token)
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}
	default LoginResponse.Seller getSellerToken(Member member, String token) {
		if (member == null && token == null && member.getSeller() == null) {
			return null;
		}
		LoginResponse.Seller response =
			LoginResponse.Seller.builder()
				.memberId(member.getMemberId())
				.sellerId(member.getSeller().getSellerId())
				.authorization(token)
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}

	/* 소셜 용 --> 소셜은 권한이 없기 때문 */
	default LoginResponse.Member socialLoginResponseDto(Member member, TokenDto tokenDto) {
		LoginResponse.Member response =
			LoginResponse.Member.builder()
				.memberId(member.getMemberId())
				.authorization(tokenDto.getAccessToken())
				.name(member.getName())
				.role(member.getRole())
				.build();

		return response;
	}
}