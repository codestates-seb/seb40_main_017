// package team017.security.auth;
//
// import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
// import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
// import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
// import org.springframework.security.oauth2.core.user.OAuth2User;
// import org.springframework.stereotype.Service;
//
// import lombok.RequiredArgsConstructor;
// import team017.member.entity.Member;
// import team017.member.entity.ProviderType;
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
// 		return null;
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
// 				throw new RuntimeException( "Looks like you're signed up with " + providerType +
// 					" account. Please use your " + findMember.getProviderType() + " account to login.");
// 			}
// 			updateMember(findMember, userInfo);
// 		} else {
// 			findMember = createMember(userInfo, providerType);
// 		}
//
// 		return MemberPrincipal.create(findMember, user.getAttributes());
// 	}
//
// 	/* client 인지 seller 인지 어떻게 ?*/
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
// 	/* 여기 부분은 이해 후 커스터 마이징 해야됨 */
// 	private Member updateMember(Member member, OAuth2UserInfo userInfo) {
// 		if (userInfo.getName() != null && !member.getName().equals(userInfo.getName())) {
// 			member.setName(userInfo.getName());
// 		}
// 		return member;
// 	}
// }
