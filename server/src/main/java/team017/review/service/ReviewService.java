package team017.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Service.BoardService;
import team017.comments.entity.Comment;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.ClientService;
import team017.review.entity.Review;
import team017.review.repository.ReviewRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    private ClientService clientService;

    private BoardService boardService;


    public Review createReview(Review review, Long clientId) {
        review.setClient(clientService.findVerifiedClient(clientId));
        verifiedClient(review);
        review.setBoard(boardService.findVerifiedBoard(review.getBoard().getBoardId()));
        verifiedBoard(review);

        return reviewRepository.save(review);
    }

    public Review findReview(Long reviewId){
        return findVerifiedReviewById(reviewId);
    }

    public Review updateReview(Review review, Long clientId){
        Review foundReview = findReview(review.getReviewId());
        verifyWriter(clientId, review.getClient().getClientId());

        Optional.ofNullable(review.getContext()).ifPresent(context -> foundReview.setContext(context));

        return reviewRepository.save(foundReview);
    }

    public void deleteReview(Long reviewId, Long clientId){
        Review foundReview = findVerifiedReviewById(reviewId);
        Long postClientId = foundReview.getClient().getClientId();
        verifyWriter(postClientId, clientId);
        reviewRepository.delete(foundReview);
    }



    private Review findVerifiedReviewById(Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.orElseThrow(() -> new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));

        return review;
    }
    public void verifyWriter(Long postUserId, Long editUserId) {
        if (!postUserId.equals(editUserId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }
    private void verifiedClient(Review review) {
        clientService.findClient(review.getClient().getClientId());
    }

    private void verifiedBoard(Review review) {
        boardService.findVerifiedBoard(review.getBoard().getBoardId());
    }

    public Page<Review> findReviews(int page, int size){
        return reviewRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

}
