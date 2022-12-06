package team017.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Entity.Board;
import team017.board.Repository.BoardRepository;
import team017.board.Service.BoardService;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.ClientService;
import team017.ord.entity.Ord;
import team017.ord.repository.OrdRepository;
import team017.ord.service.OrdService;
import team017.review.entity.Review;
import team017.review.repository.ReviewRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ClientService clientService;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final OrdRepository ordRepository;

    @Transactional
    public Review createReview(Review review, Long clientId) {

        // 존재하는 회원인지 확인
        review.setClient(clientService.findClient(clientId));
        verifiedClient(review);

        // 존재하는 게시판인지 확인
        review.setBoard(boardService.findVerifiedBoard(review.getBoard().getBoardId()));
        verifiedBoard(review);

        List<Ord> ordList = ordRepository.findByClient_ClientId(clientId);
        List<Ord> hasProduct = ordList.stream()
            .filter(ord -> ord.getProduct().getProductId() == review.getBoard().getProduct().getProductId())
            .filter(ord -> ord.getStatus() == Ord.OrdStatus.PAY_COMPLETE)
            .collect(Collectors.toList());
        if (hasProduct.size() == 0) {
            throw new BusinessLogicException(ExceptionCode.NOT_BUY_REVIEW);
        }

        Review savedReview = reviewRepository.save(review);

        //리뷰의 평균, 총 리뷰수 저장
        Board board = boardService.findVerifiedBoard(savedReview.getBoard().getBoardId());
        board.setReviewAvg(reviewRepository.findbyReviewAvg(board.getBoardId()));
        board.setReviewNum(board.getReviewNum() + 1);
        boardRepository.save(board);

        return savedReview;
    }

    @Transactional(readOnly = true)
    public Review findReview(Long reviewId){
        return findVerifiedReviewById(reviewId);
    }

//    public Review updateReview(Review review, Long clientId){
//        Review foundReview = findReview(review.getReviewId());
//        verifyWriter(clientId, foundReview.getClient().getClientId()); // 작성자와 수정자가 같은지 확인
//        Optional.ofNullable(review.getContext()).ifPresent(context -> foundReview.setContext(context));
//        Optional.ofNullable(review.getImage()).ifPresent(image -> foundReview.setImage(image));
//
//        return reviewRepository.save(foundReview);
//    }

    public void deleteReview(Long reviewId, Long clientId){
        Review foundReview = findVerifiedReviewById(reviewId);
        Long postClientId = foundReview.getClient().getClientId();
        verifyWriter(postClientId, clientId);
        reviewRepository.delete(foundReview);
        Board board = boardRepository.findById(foundReview.getBoard().getBoardId())
            .orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        /* 만약 보드에서 리뷰가 없으면 평균값을 0으로 만듦 -> repository 에서 찾지 못함 */
        if (board.getReviewList().size() == 0) {
            board.setReviewAvg(0);
        } else {
            board.setReviewAvg(reviewRepository.findbyReviewAvg(board.getBoardId()));
        }
        board.setReviewNum(board.getReviewNum() - 1);
        boardRepository.save(board);
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
        boardService.getBoard(review.getBoard().getBoardId());
    }

//    public Page<Review> findReviews(int page, int size){
//        return reviewRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
//    }

    public Page<Review> findReviewByBoards(Long boardId, int page, int size){
        return reviewRepository.findByBoard_BoardId(boardId, PageRequest.of(page, size, Sort.by("reviewId").descending()));
    }
}
