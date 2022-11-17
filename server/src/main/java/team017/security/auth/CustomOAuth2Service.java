// package team017.security.auth;
//
// import java.util.List;
//
// import org.springframework.security.authentication.InternalAuthenticationServiceException;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import team017.member.entity.Client;
// import team017.member.entity.Member;
// import team017.member.entity.ProviderType;
// import team017.member.entity.Seller;
// import team017.member.repository.MemberRepository;
//
// @Service
// @RequiredArgsConstructor
// public class CustomOAuth2Service extends DefaultOAuth2UserService {
//
// 	private final MemberRepository memberRepository;
//
// 	@Override
// 	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
// 		OAuth2User user = super.loadUser(userRequest);
//
// 		try {
// 			return this.process(userRequest, user);
// 		} catch (AuthenticationException exception) {
// 			throw exception;
// 		} catch (Exception ex) {
// 			ex.printStackTrace();
// 			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
// 		}
// 	}
//
// 	/* 소셜 로그인 들어가기 전 틀 구현? 계속 하면 수정해야됨. */
// 	private OAuth2User process(OAuth2UserRequest request, OAuth2User user) {
// 		ProviderType providerType = ProviderType.valueOf(request.getClientRegistration().getRegistrationId());
//
// 		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
// 		Member findMember = memberRepository.findMemberByEmail(userInfo.getEmail());
//
// 		if (findMember != null) {
// 			if (providerType != findMember.getProviderType()) {
// 				throw new RuntimeException("회원은 존재하나 지금의 소셜이 아닙니다.");
// 			}
// 		} else {
// 			/* 등록되지 않으면 저장이 필요 */
// 			findMember = createMember(userInfo, providerType);
// 		}
//
// 		return MemberPrincipal.create(findMember, user.getAttributes());
// 	}
//
// 	private Member createMember(OAuth2UserInfo userInfo, ProviderType providerType) {
// 		Member member = new Member(
// 			userInfo.getEmail(),
// 			userInfo.getName(),
// 			providerType
// 		);
//
// 		return memberRepository.saveAndFlush(member);
// 	}
//
// 	// private Member createClient(OAuth2UserInfo userInfo, ProviderType providerType) {
// 	// 	Member member = new Member(
// 	// 		userInfo.getEmail(),
// 	// 		userInfo.getName(),
// 	// 		"PASSWORD",
// 	// 		providerType,
// 	// 		"CLIENT",
// 	// 		List.of("CLIENT")
// 	// 	);
// 	// 	member.setClient(new Client());
// 	//
// 	// 	return memberRepository.saveAndFlush(member);
// 	// }
// 	// private Member createSeller(OAuth2UserInfo userInfo, ProviderType providerType) {
// 	// 	Member member = new Member(
// 	// 		userInfo.getEmail(),
// 	// 		userInfo.getName(),
// 	// 		"PASSWORD",
// 	// 		providerType,
// 	// 		"SELLER",
// 	// 		List.of("SELLER")
// 	// 	);
// 	// 	member.setSeller(new Seller());
// 	//
// 	// 	return memberRepository.saveAndFlush(member);
// 	// }
// }
