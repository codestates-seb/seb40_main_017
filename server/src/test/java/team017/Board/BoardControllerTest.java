package team017.Board;

import com.google.gson.Gson;
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
import org.springframework.test.web.servlet.ResultActions;
import team017.board.Controller.BoardController;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Mapper.BoardMapper;
import team017.board.Service.BoardService;
import team017.product.Entity.Product;
import team017.product.Mapper.ProductMapper;
import team017.product.Service.ProductService;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static team017.util.ApiDocumentUtils.getRequestPreProcessor;
import static team017.util.ApiDocumentUtils.getResponsePreProcessor;

@WebMvcTest(BoardController.class) //test 대상 controller
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class BoardControllerTest {

    @Autowired private MockMvc mockMvc;  // MockMvc 객체를 주입
    @Autowired private Gson gson;

    //의존하는 객체와의 관계를 단절을 위해 가짜 객체 주입
    @MockBean private BoardService boardService;
    @MockBean private BoardMapper boardMapper;
    @MockBean private ProductService productService;
    @MockBean private ProductMapper productMapper;

    @Test
    public void postBoardTest() throws Exception {

        // given (request body)
        BoardPostDto post = new BoardPostDto(
                1L,
                "쌀을 팝니다",
                "이 쌀은 맛이 좋아요!",
                1000,
                200,
                2);
        String content = gson.toJson(post);

        BoardResponseDto responseDto = new BoardResponseDto(
                1L,
                1L,
                1L,
                "박생산",
                "쌀을 팝니다",
                "이 쌀은 맛이 좋아요!",
                1000,
                200,
                2,
                null,
                Product.ProductStatus.PRD_SELLING,
                0,
                LocalDateTime.now(),
                LocalDateTime.now(),
                0.0,
                0);

        // Mock 객체를 이용한 Stubbing
        given(boardService.createBoard(post)).willReturn(responseDto);
//        given(productService.createProduct(post)).willReturn()
//        given(boardMapper.boardPostDtoToBoard(post)).willReturn();


        // when
        ResultActions actions =
                mockMvc.perform(
                        // MockMvc의 perform() 메서드로 POST 요청을 전송
                        post("/boards")
                                .content(content)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .with(csrf())
                );

        // then
        actions
                .andExpect(status().isCreated()) //response에 대한 기대 값 검증
                //.andExpect(jsonPath("$.data."))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(
                        // (10) API 문서 스펙 정보 추가
                        "post-board",                  // (9-1) API 문서 스니핏의 식별자 역할
                        getRequestPreProcessor(),      // (9-2) request와 response에 해당하는 문서 영역을 전처리
                        getResponsePreProcessor(),     // (9-3) request와 response에 해당하는 문서 영역을 전처리
                        requestFields(                 // (9-4) request body
                                List.of(
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("판매자 이름"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("stock").type(JsonFieldType.NUMBER).description("재고"),
                                        fieldWithPath("category").type(JsonFieldType.NUMBER).description("카테고리")
                                )
                        ),
                        responseFields(                 // (9-5) response body
                                List.of(
                                        fieldWithPath("boardId").type(JsonFieldType.NUMBER).description("게시판 ID"),
                                        fieldWithPath("productId").type(JsonFieldType.NUMBER).description("상품 ID"),
                                        fieldWithPath("sellerId").type(JsonFieldType.NUMBER).description("판매자 ID"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("가격"),
                                        fieldWithPath("stock").type(JsonFieldType.NUMBER).description("재고"),
                                        fieldWithPath("category").type(JsonFieldType.NUMBER).description("카테고리"),
                                        fieldWithPath("sellPhotoId").type(JsonFieldType.NUMBER).description("이미지 ID"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("상태"),
                                        fieldWithPath("view").type(JsonFieldType.NUMBER).description("조회수"),
                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("수정일자"),
                                        fieldWithPath("avg").type(JsonFieldType.NUMBER).description("평균 평점"),
                                        fieldWithPath("sold_stock").type(JsonFieldType.NUMBER).description("팔린 갯수")


                                )
                        )
                ));
    }



}