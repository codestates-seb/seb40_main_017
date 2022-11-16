package team017.review.mapper;

import org.mapstruct.Mapper;
import team017.board.Entity.Board;
import team017.member.entity.Client;
import team017.review.dto.ReviewPatchDto;
import team017.review.dto.ReviewPostDto;
import team017.review.dto.ReviewResponseDto;
import team017.review.entity.Review;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    default Review reviewPostDtoToReview(ReviewPostDto reviewPostDto) {
        if ( reviewPostDto == null ) {
            return null;
        }
        Client.ClientBuilder client = Client.builder();
        client.build();

        Board board = new Board();
        board.setBoardId(reviewPostDto.getBoardId());

        Review review = new Review();
        review.setClient(client.build());
        review.setBoard(board);
        review.setImage(reviewPostDto.getImage());
        review.setContext(reviewPostDto.getContext());

        return review;
    }

    default Review reviewPatchDtoToReview(ReviewPatchDto reviewPatchDto) {
        if ( reviewPatchDto == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();
        client.build();

        Board board = new Board();
        board.setBoardId(reviewPatchDto.getBoardId());

        Review review = new Review();
        review.setClient(client.build());
        review.setBoard(board);
        review.setImage(reviewPatchDto.getImage());
        review.setReviewId( reviewPatchDto.getReviewId());
        review.setContext( reviewPatchDto.getContext());

        return review;
    }

    default ReviewResponseDto reviewToReviewResponseDto(Review review) {
        if ( review == null ) {
            return null;
        }

        Client.ClientBuilder client = Client.builder();
        client.build();

        review.setReviewId(review.getReviewId());

        Board board = new Board();
        board.setBoardId(review.getBoard().getBoardId());

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto();
        reviewResponseDto.setClientId(client.build().getClientId());
        reviewResponseDto.setBoardId(board.getBoardId());
        reviewResponseDto.setContext(review.getContext());
        reviewResponseDto.setName(review.getClient().getMember().getName());
        reviewResponseDto.setCreatedAt(review.getCreatedAt());
        reviewResponseDto.setModifiedAt(review.getModifiedAt());

        return reviewResponseDto;
    }

    List<ReviewResponseDto> reviewToReviewResponseDtos(List<Review> reviews);
}