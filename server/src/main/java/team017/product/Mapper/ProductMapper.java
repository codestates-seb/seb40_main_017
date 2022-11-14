package team017.product.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team017.board.Dto.BoardPostDto;
import team017.member.entity.Seller;
import team017.product.Entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product boardPostDtoToProduct(BoardPostDto boardPostDto);

}
