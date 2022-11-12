package team017.product.Mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import team017.board.Dto.BoardPostDto;
import team017.product.Entity.Product;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-12T21:14:04+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11.0.16.1 (Azul Systems, Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product boardPostDtoToProduct(BoardPostDto boardPostDto) {
        if ( boardPostDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setPrice( boardPostDto.getPrice() );
        product.setStock( boardPostDto.getStock() );
        product.setCategory( boardPostDto.getCategory() );

        return product;
    }
}
