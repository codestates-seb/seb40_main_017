// package team017.member;
//
// import static org.mockito.BDDMockito.given;
// import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
// import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
// import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.ResultActions;
//
// import com.google.gson.Gson;
//
// import team017.member.controller.MemberController;
// import team017.member.dto.MemberDto;
// import team017.member.entity.Client;
// import team017.member.entity.Member;
// import team017.member.mapper.MemberMapper;
// import team017.member.service.MemberService;
//
// @WebMvcTest(MemberController.class)
// @MockBean(JpaMetamodelMappingContext.class)
// @AutoConfigureRestDocs
// public class MemberControllerTest {
// 	@Autowired
// 	private MockMvc mockMvc;
//
// 	@MockBean
// 	private MemberService memberService;
//
// 	@MockBean
// 	private MemberMapper mapper;
//
// 	@Autowired
// 	private Gson gson;
//
// 	/* 응답 바디를 계속 못 받는 중.. */
// 	@Test
// 	@DisplayName("회원 가입")
// 	public void postMemberTest() throws Exception {
// 		MemberDto.Post post =
// 			new MemberDto.Post("test1@naver.com", "테스트1", "password1", "password1",
// 				"010-1111-1111", "서울시 송파구", "CLIENT");
// 		String content = gson.toJson(post);
//
// 		MemberDto.ClientResponseDto clientDto = new MemberDto.ClientResponseDto();
// 		clientDto.setMemberId(1L);
// 		clientDto.setClientId(1L);
// 		clientDto.setName("테스트1");
// 		clientDto.setEmail("test1@naver.com");
// 		clientDto.setPhone("010-1111-1111");
// 		clientDto.setAddress("서울시 송파구");
// 		clientDto.setRole("CLIENT");
//
// 		System.out.println("ClientDto 확인, Client 이름  : " + clientDto.getName());
//
// 		given(mapper.memberDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
// 		given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
//
// 		/* Response Body 가 Null 이 나는 이유 ? -> 이 mapper 때문이 아닐까 */
// 		given(mapper.memberToClientDto(Mockito.any(Member.class), Mockito.any(Client.class))).willReturn(clientDto);
//
// 		ResultActions actions = mockMvc.perform(
// 			post("/members")
// 				.accept(MediaType.APPLICATION_JSON)
// 				.content(content)
// 				.contentType(MediaType.APPLICATION_JSON)
// 		);
//
// 		MvcResult result =
// 			actions
// 				// .andExpect(status().isCreated())
// 				.andReturn();
//
// 		System.out.println("요청 : " + result.getRequest().getContentAsString());
// 		System.out.println("응답 : " + result.getResponse().getContentAsString());
// 		System.out.println("client NAME : " + clientDto.getName());
// 	}
//
// 	@Test
// 	@DisplayName("회원 탈퇴")
// 	public void deleteMemberTest() throws Exception {
// 		Member member1 = new Member();
// 		member1.setMemberId(1L);
//
// 		String content = gson.toJson(member1);
//
// 		long memberId = 1;
//
// 		ResultActions actions = mockMvc.perform(
// 			delete("/members/" +  memberId)
// 				.accept(MediaType.APPLICATION_JSON)
// 				.contentType(MediaType.APPLICATION_JSON)
// 		);
//
// 		actions.andExpect(status().isOk())
// 			.andReturn();
// 	}
// }
