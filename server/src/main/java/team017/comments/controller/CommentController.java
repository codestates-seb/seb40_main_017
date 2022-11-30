package team017.comments.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team017.comments.dto.CommentPatchDto;
import team017.comments.dto.CommentPostDto;
import team017.comments.entity.Comment;
import team017.comments.mapper.CommentMapper;
import team017.comments.service.CommentService;
import team017.global.response.MultiResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("comments")
@AllArgsConstructor
@Validated
@Slf4j
public class CommentController {

    private final CommentService commentService;

    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity postComment(@Valid @RequestBody CommentPostDto commentPostDto) {
        Comment comment = commentService.createComment(
                commentMapper.commentPostDtoToComment(commentPostDto), commentPostDto.getMemberId());

        return new ResponseEntity<>((commentMapper.commentToCommentResponseDto(comment)), HttpStatus.CREATED);
    }

    @GetMapping("/{board-Id}")
    public ResponseEntity getComment(@PathVariable("board-Id") @Positive Long boardId,
                                     @Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {

        Page<Comment> commentPage = commentService.findCommentByBoard(boardId,page - 1, size);
        List<Comment> commentList = commentPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(commentMapper.commentToCommentResponseDtos(commentList),
                        commentPage), HttpStatus.OK);
    }

    @PatchMapping("/{comment-Id}")
    public ResponseEntity patchComment(@PathVariable("comment-Id") @Positive Long commentId,
                                       @Valid @RequestBody CommentPatchDto commentPatchDto) {
        commentPatchDto.setCommentId(commentId);
        Comment comment = commentService.updateComment(
                commentMapper.commentPatchDtoToComment(commentPatchDto), commentPatchDto.getMemberId());

        return new ResponseEntity<>(commentMapper.commentToCommentResponseDto(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{comment-Id}")
    public ResponseEntity deleteComment(@PathVariable("comment-Id") @Positive Long commentId,
                                        @Positive @RequestParam Long memberId) {
        commentService.deleteComment(commentId, memberId);
        String message = "Success!";

        return ResponseEntity.ok(message);
    }
}
