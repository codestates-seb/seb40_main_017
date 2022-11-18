package team017.image.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.image.Entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long>{
}
