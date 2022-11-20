//package team017.member;
//
//import java.util.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.google.gson.Gson;
//
//import team017.member.entity.Client;
//import team017.member.entity.Member;
//import team017.member.entity.Seller;
//import team017.member.repository.MemberRepository;
//
//@Transactional
//@SpringBootTest
//@AutoConfigureMockMvc
//class ClientControllerTest {
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private Gson gson;
//
//	@Autowired
//	private MemberRepository memberRepository;
//
//	// @BeforeEach
//	//  void init() throws Exception{
//	// 	Member member1 = new Member();
//	// 	member1.setMemberId(1L);
//	// 	member1.setEmail("test1@naver.com");
//	// 	member1.setName("테스트1");
//	// 	member1.setPassword("password1");
//	// 	member1.setAddress("address");
//	// 	member1.setPhone("010-1111-1111");
//	// 	member1.setClient(new Client(1L));
//	//
//	// 	Member member2 = new Member();
//	// 	member2.setMemberId(2L);
//	// 	member2.setEmail("test2@naver.com");
//	// 	member2.setName("테스트2");
//	// 	member2.setPassword("password2");
//	// 	member2.setAddress("address");
//	// 	member2.setPhone("010-2222-2222");
//	// 	member2.setSeller(new Seller(1L, null));
//	//
//	// 	memberRepository.save(member1);
//	// 	memberRepository.save(member2);
//	//
//	// 	System.out.println("# client ID : " + member1.getClient().getClientId());
//	// 	System.out.println("# client NAME : " + member1.getClient().getMember().getName());
//	// }
//
//	@Test
//	void getClientTest() throws Exception {
//		// given
//		long clientId = 1;
//
//		// when
//		ResultActions actions = mockMvc.perform(
//			get("/members/client/1")
//				.accept(MediaType.APPLICATION_JSON)
//				.contentType(MediaType.APPLICATION_JSON)
//		);
//
//		// then
//		MvcResult result = actions
//			// .andExpect(status().isOk())
//			// .andExpect(jsonPath("$.name").value("테스트1"))
//			// .andExpect(jsonPath("$.email").value("test1@naver.com"))
//			// .andExpect(jsonPath("$.phone").value("010-1111-1111"))
//			.andReturn();
//		System.out.println("응답 : " + result.getResponse().getContentAsString());
//	}
//}
