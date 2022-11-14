package team017.product.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Dto.BoardPatchDto;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.product.Entity.Product;
import team017.product.Repository.ProductRepository;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findVerifiedProduct(long productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Product findProduct = optionalProduct.orElseThrow(()->
                new BusinessLogicException(ExceptionCode.PRODUCT_NOT_FOUND));

        return findProduct;
    }
}
