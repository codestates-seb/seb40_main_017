package team017.board.Mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.product.Entity.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T21:54:07+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board boardPostDtoToBoard(BoardPostDto boardPostDto) {
        if ( boardPostDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setTitle( boardPostDto.getTitle() );
        board.setContent( boardPostDto.getContent() );

        return board;
    }

    @Override
    public Board productToBoard(Product product, BoardPostDto boardPostDto) {
        if ( product == null && boardPostDto == null ) {
            return null;
        }

        Board board = new Board();

        if ( product != null ) {
            board.setSeller( product.getSeller() );
        }
        if ( boardPostDto != null ) {
            board.setTitle( boardPostDto.getTitle() );
            board.setContent( boardPostDto.getContent() );
        }

        return board;
    }

    @Override
    public BoardResponseDto productToBoardResponseDto(Product product, Board board) {
        if ( product == null && board == null ) {
            return null;
        }

        BoardResponseDto.BoardResponseDtoBuilder boardResponseDto = BoardResponseDto.builder();

        if ( product != null ) {
            boardResponseDto.productId( product.getProductId() );
            boardResponseDto.price( product.getPrice() );
            boardResponseDto.stock( product.getStock() );
            boardResponseDto.category( product.getCategory() );
            boardResponseDto.status( product.getStatus() );
        }
        if ( board != null ) {
            boardResponseDto.boardId( board.getBoardId() );
            boardResponseDto.title( board.getTitle() );
            boardResponseDto.content( board.getContent() );
            boardResponseDto.viewCount( board.getViewCount() );
            boardResponseDto.createdAt( board.getCreatedAt() );
            boardResponseDto.modifiedAt( board.getModifiedAt() );
        }

        return boardResponseDto.build();
    }
}
