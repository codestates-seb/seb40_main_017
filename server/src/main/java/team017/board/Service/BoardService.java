package team017.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.board.Repository.BoardRepository;
import team017.product.Entity.Product;
import team017.product.Mapper.ProductMapper;
import team017.product.Repository.ProductRepository;
import team017.product.Service.ProductService;

@Service
@RequiredArgsConstructor
public class BoardService {

    // 상품은 재고 수정 하지 못함, 상품은 게시글을 통해서만 수정가능!
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public BoardResponseDto createBoard(BoardPostDto boardPostDto){

        //판매자 존재 여부 확인
        //verifyExistsSellerId(boardPostDto.getSellerId());

        //상품 등록
        Product product = createProduct(boardPostDto);

        //게시글 등록
        Board board = boardMapper.productToBoard(product ,boardPostDto);
        boardRepository.save(board);

        BoardResponseDto boardResponseDto = boardMapper.productToBoardResponseDto(product,board);
        return boardResponseDto;
    }

    private Product createProduct(BoardPostDto boardPostDto) {
        Product product = productMapper.boardPostDtoToProduct(boardPostDto);
        productRepository.save(product);
        return product;

    }


}
