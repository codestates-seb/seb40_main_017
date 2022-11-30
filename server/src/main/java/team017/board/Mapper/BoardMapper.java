package team017.board.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Dto.BoardTotalResponseDto;
import team017.board.Entity.Board;
import team017.member.entity.Seller;
import team017.ord.dto.OrdSellerResponseDto;
import team017.product.Entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {

    Board boardPostDtoToBoard(BoardPostDto boardPostDto);

    List<OrdSellerResponseDto> ordToOrdSellerResponseDtos(List<Board> boards);

    @Mapping(target = "status", expression = "java(product.getStatus())")
    @Mapping(target = "sellerId", expression = "java(board.getSeller().getSellerId())")
    @Mapping(target = "name", expression = "java(board.getSeller().getMember().getName())")
    BoardResponseDto productToBoardResponseDto(Product product, Board board);

    @Mapping(target = "sellerId", expression = "java(board.getSeller().getSellerId())")
    @Mapping(target = "sellerImage", expression = "java(board.getSeller().getImageUrl())")
    @Mapping(target = "name", expression = "java(board.getSeller().getMember().getName())")
    BoardTotalResponseDto productToBoardTotalResponseDto(Product product, Board board);

    default OrdSellerResponseDto ordToOrdSellerResponseDto(Board board){
        if( board == null){
            return null;
        }

        Seller seller = new Seller();
        seller.setSellerId(board.getSeller().getSellerId());

        Product product = new Product();
        product.setProductId(board.getProduct().getBoard().getBoardId());

        OrdSellerResponseDto ordResponseDto = new OrdSellerResponseDto();
        ordResponseDto.setBoardId(product.getProductId());
        ordResponseDto.setSellerId(seller.getSellerId());
        ordResponseDto.setTitle(board.getProduct().getBoard().getTitle());
        ordResponseDto.setName(board.getSeller().getMember().getName());
        ordResponseDto.setPrice(board.getProduct().getPrice());
        ordResponseDto.setPhone(board.getSeller().getMember().getPhone());
        ordResponseDto.setStock(board.getProduct().getStock());
        ordResponseDto.setCategory(board.getProduct().getCategory());
        ordResponseDto.setLeftStock(board.getProduct().getLeftStock());
        ordResponseDto.setCreateAt(board.getCreatedAt());
        ordResponseDto.setModifiedAt(board.getModifiedAt());

        return ordResponseDto;
    }
}