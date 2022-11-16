package team017.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Service.BoardService;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.ClientService;
import team017.review.entity.Review;
import team017.review.repository.ReviewRepository;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    private ClientService clientService;

    private BoardService boardService;


    public Review createReview(Review review, Long clientId){
        review.setClient(clientService.findClient(clientId));
    }

    private Review findVerifiedReviewById(Long reviewId){
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        return review;
    }


}
