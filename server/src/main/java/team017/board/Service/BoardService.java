package team017.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.board.Repository.BoardRepository;
import team017.member.entity.Seller;
import team017.member.service.SellerService;
import team017.product.Entity.Product;
import team017.product.Mapper.ProductMapper;
import team017.product.Repository.ProductRepository;
import team017.product.Service.ProductService;

@Service
@RequiredArgsConstructor
public class BoardService {

    // 상품은 재고 수정 하지 못함, 상품은 게시글을 통해서만 수정가능!

    private final SellerService sellerService;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public BoardResponseDto createBoard(BoardPostDto boardPostDto){

        //판매자 존재 여부 확인
        Seller findSeller = sellerService.findVerifiedSeller(boardPostDto.getSellerId());

        //상품 등록
        Product product = createProduct(findSeller,boardPostDto);

        //게시글 등록
        Board board = boardMapper.boardPostDtoToBoard(boardPostDto);
        board.setSeller(findSeller);
        board.setProduct(product);
        boardRepository.save(board);

        BoardResponseDto boardResponseDto = boardMapper.productToBoardResponseDto(product,board);
        return boardResponseDto;
    }

    public Product createProduct(Seller seller, BoardPostDto boardPostDto) {
        Product product = productMapper.boardPostDtoToProduct(boardPostDto);
        product.setStatus("selling");
        product.setSeller(seller);
        return productRepository.save(product);

//        System.out.print("product 상태 메시지:");
//        System.out.println(product.getStatus());

//        //product의 status 문제로 추가
//        Product findProduct= productService.findVerifiedProduct(product.getProductId());
//        System.out.print("findProduct 상태 메시지:");
//        System.out.println(findProduct.getStatus());
//        return findProduct;

    }




}
