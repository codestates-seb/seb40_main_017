package team017.product.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPatchDto;
import team017.board.Dto.BoardPostDto;
import team017.board.Entity.Board;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Seller;
import team017.product.Entity.Product;
import team017.product.Mapper.ProductMapper;
import team017.product.Repository.ProductRepository;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Product createProduct(Seller seller, BoardPostDto boardPostDto) {
        Product product = productMapper.boardPostDtoToProduct(boardPostDto);
        product.setStatus(Product.ProductStatus.valueOf("PRD_SELLING"));
        product.setSeller(seller);
        Product productSaved = productRepository.save(product);
        return productSaved;
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

}
