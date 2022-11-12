package team017.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team017.review.service.ReviewService;

@RestController
@RequestMapping("/boards/{board_id}")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;



}
