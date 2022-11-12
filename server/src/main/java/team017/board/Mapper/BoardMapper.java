package team017.board.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.product.Entity.Product;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardPostDto boardPostDto);

    //@Mapping(target = "seller.Set", expression = "java(product.getSeller().getSellerId())")
    Board productToBoard(Product product ,BoardPostDto boardPostDto);
    //@Mapping(target = "status", expression = "java(product.getStatus())")
    BoardResponseDto productToBoardResponseDto(Product product , Board board);

}
