package team017.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.review.entity.Review;
import team017.review.repository.ReviewRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private ReviewRepository reviewRepository;

}
