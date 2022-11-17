package team017.member;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team017.util.ApiDocumentUtils.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.google.gson.Gson;

import team017.member.controller.MemberController;
import team017.member.dto.MemberDto;
import team017.member.entity.Client;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.mapper.MemberMapper;
import team017.member.repository.MemberRepository;
import team017.member.service.MemberService;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@MockBean
	private MemberMapper mapper;

	@MockBean
	private MemberRepository memberRepository;

	@Autowired
	private Gson gson;

	/* 응답 바디를 계속 못 받는 중.. */
	@Test
	@DisplayName("회원 가입")
	public void postMemberTest() throws Exception {
		Member member = new Member();
		member.setMemberId(1L);
		member.setEmail("test1@naver.com");
		member.setName("테스트1");
		member.setPassword("password1");
		member.setAddress("address");
		member.setPhone("010-1111-1111");
		member.setClient(new Client(1L));

		MemberDto.Post post =
			new MemberDto.Post("test1@naver.com", "테스트1", "password1", "password1",
				"010-1111-1111", "서울시 송파구", "CLIENT");
		String content = gson.toJson(post);

		MemberDto.ClientResponseDto clientDto = new MemberDto.ClientResponseDto();
		clientDto.setMemberId(1L);
		clientDto.setClientId(1L);
		clientDto.setName("테스트1");
		clientDto.setEmail("test1@naver.com");
		clientDto.setPhone("010-1111-1111");
		clientDto.setAddress("서울시 송파구");
		clientDto.setRole("CLIENT");

		// System.out.println("ClientDto 확인, Client 이름  : " + clientDto.getName());

		given(mapper.memberDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(member);
		given(memberService.createMember(Mockito.any(Member.class))).willReturn(member);

		/* Response Body 가 Null 이 나는 이유 ? -> 이 mapper 때문이 아닐까 */
		given(mapper.memberToClientDto(Mockito.any(Member.class), Mockito.any(Client.class))).willReturn(clientDto);

		ResultActions actions = mockMvc.perform(
			post("/members/signup")
				.accept(MediaType.APPLICATION_JSON)
				.content(content)
				.contentType(MediaType.APPLICATION_JSON)
		);

		MvcResult result =
			actions
				.andExpect(status().isCreated())
				.andDo(document(
					"post-member",
					getRequestPreProcessor(),
					getResponsePreProcessor(),
					requestFields(
						List.of(
							fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
							fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
							fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
							fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 확인"),
							fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
							fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
							fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
						)
					),
					responseFields(
						List.of(
							fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
							fieldWithPath("clientId").type(JsonFieldType.NUMBER).description("소비자 식별자"),
							fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
							fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
							fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
							fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
							fieldWithPath("role").type(JsonFieldType.STRING).description("역할")
						)
					)
				))
				.andReturn();
		// System.out.println("요청 : " + result.getRequest().getContentAsString());
		// System.out.println("응답 : " + result.getResponse().getContentAsString());
		// System.out.println("client NAME : " + clientDto.getName());
	}

	@Test
	@DisplayName("회원 탈퇴")
	public void deleteMemberTest() throws Exception {
		Member member = new Member();
		member.setMemberId(1L);
		member.setClient(new Client(1L));

		long memberId = 1;
		String message = "Success!";

		doNothing().when(memberService).deleteMember(memberId);

		ResultActions actions = mockMvc.perform(
			delete("/members/" +  memberId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		);

		actions.andExpect(status().isOk())
			.andDo(document("delete-member"));
	}
}
