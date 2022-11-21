package team017.product.Mapper;

import org.mapstruct.Mapper;
import team017.board.Dto.BoardPostDto;
import team017.product.Entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product boardPostDtoToProduct(BoardPostDto boardPostDto);

}
