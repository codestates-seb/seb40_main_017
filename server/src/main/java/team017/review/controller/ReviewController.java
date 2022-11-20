package team017.review.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team017.board.Entity.Board;
import team017.global.response.MultiResponseDto;
import team017.review.dto.ReviewPatchDto;
import team017.review.dto.ReviewPostDto;
import team017.review.dto.ReviewResponseDto;
import team017.review.entity.Review;
import team017.review.mapper.ReviewMapper;
import team017.review.service.ReviewService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/boards")
@Validated
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMapper reviewMapper;


    @PostMapping("/{board-id}/reviews")
    public ResponseEntity postReview(@PathVariable("board-id") @Positive Long boardId,
                                     @Valid @RequestBody ReviewPostDto reviewPostDto) {
        reviewPostDto.setBoardId(boardId);
        Review review = reviewService.createReview(
                reviewMapper.reviewPostDtoToReview(reviewPostDto), reviewPostDto.getClientId());

        return new ResponseEntity<>((
                reviewMapper.reviewToReviewResponseDto(review)), HttpStatus.CREATED);
    }

    @GetMapping("/reviews/{board-id}")
    public ResponseEntity getReview(@PathVariable("board-id") @Positive Long boardId,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {

        Page<Review> reviewPage = reviewService.findReviewByBoards(boardId,page - 1, size);
        List<Review> reviewList = reviewPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(reviewMapper.reviewToReviewResponseDtos(reviewList), reviewPage), HttpStatus.OK
        );
    }

    @PatchMapping("/reviews/{review-id}")
    public ResponseEntity patchReview(@PathVariable("review-id") @Positive Long reviewId,
                                      @Valid @RequestBody ReviewPatchDto reviewPatchDto) {
        reviewPatchDto.setReviewId(reviewId);
        Review review = reviewService.updateReview(
                reviewMapper.reviewPatchDtoToReview(reviewPatchDto), reviewPatchDto.getClientId());

        return new ResponseEntity<>(reviewMapper.reviewToReviewResponseDto(review), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{review-id}")
    public ResponseEntity deleteReview(
            @PathVariable("review-id") @Positive Long reviewId,
            @Positive @RequestParam Long clientId) {

        reviewService.deleteReview(reviewId, clientId);
        String message = "Success!";

        return ResponseEntity.ok(message);
    }
}
