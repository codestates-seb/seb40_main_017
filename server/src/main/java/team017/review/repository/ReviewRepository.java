package team017.review.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team017.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT round(avg(r.star),1) as reviewAvg FROM Review as r WHERE r.board.boardId = :boardId")
    Double findbyReviewAvg(@Param("boardId") Long boardId);

}

