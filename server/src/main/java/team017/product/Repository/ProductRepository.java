package team017.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.product.Entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


}
