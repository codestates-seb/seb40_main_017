package team017.board.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Dto.BoardTotalResponseDto;
import team017.board.Entity.Board;
import team017.product.Entity.Product;
import team017.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardPostDto boardPostDto);
    @Mapping(target = "status", expression = "java(product.getStatus())")
    @Mapping(target = "sellerId", expression = "java(board.getSeller().getSellerId())")
    @Mapping(target = "name", expression = "java(board.getSeller().getMember().getName())")
    BoardResponseDto productToBoardResponseDto(Product product, Board board);

    @Mapping(target = "sellerId", expression = "java(board.getSeller().getSellerId())")
    @Mapping(target = "sellerImage", expression = "java(board.getSeller().getImageUrl())")
    @Mapping(target = "name", expression = "java(board.getSeller().getMember().getName())")
    BoardTotalResponseDto productToBoardTotalResponseDto(Product product, Board board);

}
