package team017.review.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import team017.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}

