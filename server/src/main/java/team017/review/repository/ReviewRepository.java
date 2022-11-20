package team017.review.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    public Page<Review> findByBoard_BoardId(Long boardId, Pageable pageable);
}

