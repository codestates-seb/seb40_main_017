package team017.board.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPatchDto;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Dto.BoardTotalResponseDto;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.board.Repository.BoardRepository;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;

import team017.member.entity.Seller;
import team017.member.service.SellerService;
import team017.product.Entity.Product;
import team017.product.Repository.ProductRepository;
import team017.product.Service.ProductService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    // 상품에서는 재고를 수정 하지 못함, 상품은 게시글을 통해서만 수정 가능!
    private final SellerService sellerService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public BoardResponseDto createBoard(BoardPostDto boardPostDto){

        //판매자 존재 여부 확인
        Seller findSeller = sellerService.findVerifiedSeller(boardPostDto.getSellerId());

        //상품 등록
        Product product = productService.createProduct(findSeller,boardPostDto);

        //게시글 등록
        Board board = boardMapper.boardPostDtoToBoard(boardPostDto);
        board.setSeller(findSeller);
        board.setProduct(product);
        product.setLeftStock(product.getStock());
        productRepository.save(product);
        boardRepository.save(board);

        BoardResponseDto boardResponseDto = boardMapper.productToBoardResponseDto(product,board);
        return boardResponseDto;
    }

    public BoardResponseDto updateBoard(long boardId, BoardPatchDto boardPatchDto) {

        //boardId 존재 여부 확인
        Board findBoard = findVerifiedBoard(boardPatchDto.getBoardId());
        //update 하는 seller 확인
        sellerService.correctSeller(findBoard.getSeller().getSellerId());

        //상품 update
        Product updatedProduct = productService.updateProduct(findBoard, boardPatchDto);

        //게시글 update (단, 재고는 update 되지 않음)
        Optional.ofNullable(boardPatchDto.getContent())
                .ifPresent(content -> findBoard.setContent(content));
        Optional.ofNullable(boardPatchDto.getTitle())
                .ifPresent(title -> findBoard.setTitle(title));

        Board updatedBoard = boardRepository.save(findBoard);

        BoardResponseDto boardResponseDto = boardMapper.productToBoardResponseDto(updatedProduct, updatedBoard);

        return boardResponseDto;

    }

    public void deleteBoard(long boardId) {
        Board findBoard = findVerifiedBoard(boardId);
        Product findProduct = productService.findVerifiedProduct(findBoard.getProduct());

        //delete 하는 seller 확인
        sellerService.correctSeller(findBoard.getSeller().getSellerId());

        //상품 삭제
        boardRepository.delete(findBoard);

        //게시글 삭제
        productRepository.delete(findProduct);
    }

    public Board findVerifiedBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(()-> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        return findBoard;
    }

    public BoardResponseDto getBoard(long boardId) {

        //게시판 존재 여부 화인
        Board findBoard = findVerifiedBoard(boardId);

        //판매자 존재 여부 확인
        Seller findSeller = sellerService.findVerifiedSeller(findBoard.getSeller().getSellerId());

        //상품 존재 여부 확인
        Product findProduct = productService.findVerifiedProduct(findBoard.getProduct());

        BoardResponseDto responseDto = boardMapper.productToBoardResponseDto(findProduct , findBoard);

        return  responseDto;
    }

    public List<BoardTotalResponseDto> getBoards(List<Board> boardList) {
        List<BoardTotalResponseDto> totalBoard = new ArrayList<>();

        Iterator iter = boardList.iterator();

        while (iter.hasNext()) {
            Board board = (Board) iter.next();
            Product product = board.getProduct();
            totalBoard.add(boardMapper.productToBoardTotalResponseDto(product, board));
        }
        return totalBoard;
    }

    public Page<Board> findBoards(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Board> findBoardsCategory(int category, int page, int size) {
        return  boardRepository.findBoardsByProduct_Category(PageRequest.of(page, size, Sort.by("createdAt").descending()), category);
    }

}
