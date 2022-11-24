package team017.review.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import team017.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    public Page<Review> findByBoard_BoardId(Long boardId, Pageable pageable);

    //리뷰 평균 구하기
    @Query("SELECT round(avg(r.star),1) as reviewAvg FROM Review as r WHERE r.board.boardId = :boardId")
    double findbyReviewAvg(@Param("boardId") Long boardId);

    //게시판 내 리뷰 수 구하기
    @Query("SELECT sum(r.reviewId) as reviewAvg FROM Review as r WHERE r.board.boardId = :boardId")
    int findbyReviewNum(@Param("boardId") Long boardId);
}

