package team017.comments.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team017.comments.dto.CommentPatchDto;
import team017.comments.dto.CommentPostDto;
import team017.comments.entity.Comment;
import team017.comments.mapper.CommentMapper;
import team017.comments.service.CommentService;
import team017.global.response.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("comment/{board-id}")
@AllArgsConstructor
@Validated
@Slf4j
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentPostDto commentPostDto){
        Comment comment = commentService.createComment(
                commentMapper.commentPostDtoToComment(commentPostDto), commentPostDto.getMemberId());

        return new ResponseEntity<>((commentMapper.commentToCommentResponseDto(comment)), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity getComment(@PathVariable("comment-Id") @Positive Long commentId){
        Comment comment = commentService.findComment(commentId);

        return new ResponseEntity<>((commentMapper.commentToCommentResponseDto(comment)),HttpStatus.OK);
    }

    @PatchMapping("/comment-id")
    public ResponseEntity patchComment(@PathVariable("comment-Id") @Positive Long commentId,
                                       @Valid @RequestBody CommentPatchDto commentPatchDto){
        commentPatchDto.setCommentId(commentId);
        Comment comment = commentService.updateComment(
                commentMapper.commentPatchDtoToComment(commentPatchDto), commentPatchDto.getMemberId());

        return new ResponseEntity<>((commentMapper.commentToCommentResponseDto(comment)),HttpStatus.OK);
    }

    @DeleteMapping("/comment-id")
    public ResponseEntity deleteComment(@PathVariable("comment-Id") @Positive Long commentId,
                                        @Positive @RequestParam Long memberId){
        commentService.deleteComment(commentId, memberId);

        return new ResponseEntity<>(
                new SingleResponseDto<>("delete success"), HttpStatus.NO_CONTENT);
    }
}
