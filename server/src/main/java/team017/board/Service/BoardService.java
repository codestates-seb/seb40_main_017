package team017.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPatchDto;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.board.Repository.BoardRepository;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;

import team017.member.entity.Seller;
import team017.member.service.SellerService;
import team017.product.Entity.Product;
import team017.product.Mapper.ProductMapper;
import team017.product.Repository.ProductRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    // 상품은 재고 수정 하지 못함, 상품은 게시글을 통해서만 수정가능!
    private final SellerService sellerService;
    private final ProductRepository productRepository;
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

    public BoardResponseDto updateBoard(long boardId, BoardPatchDto boardPatchDto) {

        //boardId 존재 여부 확인
        Board findBoard = findVerifiedBoard(boardPatchDto.getBoardId());

        //상품 update
        Product updatedProduct = updateProduct(findBoard, boardPatchDto);

        //게시글 update (단, 재고는 update 되지 않음)
        Optional.ofNullable(boardPatchDto.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(boardPatchDto.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));

        Board updatedBoard = boardRepository.save(findBoard);

        BoardResponseDto boardResponseDto = boardMapper.productToBoardResponseDto(updatedProduct, updatedBoard);

        return boardResponseDto;

    }

    public Product updateProduct(Board findBoard, BoardPatchDto boardPatchDto) {

        //productId 확인
        Product findProduct = findVerifiedProduct(findBoard.getProduct());

        //상품 update
        Optional.ofNullable(boardPatchDto.getPrice())
                .ifPresent(price -> findProduct.setPrice(price));
        Optional.ofNullable(boardPatchDto.getStatus())
                .ifPresent(status ->findProduct.setStatus(status));
        Optional.ofNullable(boardPatchDto.getCategory())
                .ifPresent(category -> findProduct.setCategory(category));


        return productRepository.save(findProduct);
    }


    public Product findVerifiedProduct(Product product) {
        Optional<Product> optionalProduct = productRepository.findById(product.getProductId());
        Product findProduct = optionalProduct.orElseThrow(()-> new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));
        return findProduct;
    }

    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;

    }

    public void deleteBoard(long boardId) {
        Board findBoard = findVerifiedBoard(boardId);
        Product findProduct = findVerifiedProduct(findBoard.getProduct());

        //상품 삭제
        boardRepository.delete(findBoard);

        //게시글 삭제
        productRepository.delete(findProduct);

    }

    public BoardResponseDto getBoard(long boardId) {

        //게시판 존재 여부 화인
        Board findBoard = findVerifiedBoard(boardId);

        //판매자 존재 여부 확인
        Seller findSeller = sellerService.findVerifiedSeller(findBoard.getSeller().getSellerId());

        //상품 존재 여부 확인
        Product findProduct = findVerifiedProduct(findBoard.getProduct());

        //조회수 ++
        addView(findBoard);

        BoardResponseDto responseDto = boardMapper.productToBoardResponseDto(findProduct , findBoard);

        return  responseDto;
    }

    private void addView(Board board) {
        Board findBoard = findVerifiedBoard(board.getBoardId());
        int viewCnt = findBoard.getView();
        viewCnt++;
        findBoard.setView(viewCnt);
        boardRepository.save(findBoard);

    }

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Board> findBoardsCategory(int category, int page, int size) {
        return  boardRepository.findBoardsByProduct_Category(PageRequest.of(page, size, Sort.by("createdAt").descending()), category);
    }
}
