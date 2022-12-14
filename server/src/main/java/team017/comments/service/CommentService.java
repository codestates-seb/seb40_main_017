package team017.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Service.BoardService;
import team017.comments.entity.Comment;
import team017.comments.repository.CommentRepository;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.MemberService;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    public Comment createComment(Comment comment, Long memberId) {
        comment.setMember(memberService.findVerifiedMember(memberId));
        verifiedMember(comment); //회원 존재 확인
        comment.setBoard(boardService.findVerifiedBoard(comment.getBoard().getBoardId()));
        verifiedBoard(comment); // 게시판 존재 확인

        return commentRepository.save(comment);
    }

    public Comment findComment(Long commentId) {
        return findVerifiedCommentById(commentId);
    }

    public Comment updateComment(Comment comment, Long memberId) {
        Comment foundComment = findComment(comment.getCommentId());
        verifyWriter(memberId, foundComment.getMember().getMemberId()); // 작성자와 수정자가 같은지 확인

        Optional.ofNullable(comment.getContext()).ifPresent(context -> foundComment.setContext(context));


        return commentRepository.save(foundComment);
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment foundComment = findVerifiedCommentById(commentId);
        Long postMemberId = foundComment.getMember().getMemberId();
        verifyWriter(postMemberId, memberId);
        commentRepository.delete(foundComment);
    }

    private Comment findVerifiedCommentById(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Comment comment = optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));

        return comment;
    }

    private void verifyWriter(Long postUserId, Long editUserId) {
        if (!postUserId.equals(editUserId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }

    private void verifiedMember(Comment comment) {
        memberService.findVerifiedMember(comment.getMember().getMemberId());
    }

    private void verifiedBoard(Comment comment) {
        boardService.findVerifiedBoard(comment.getBoard().getBoardId());
    }

    public Page<Comment> findComments(int page, int size) {
        return commentRepository.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Comment> findCommentByBoard(Long boardId, int page, int size) {
        return commentRepository.findByBoard_BoardId(boardId, PageRequest.of(page, size, Sort.by("commentId").descending()));
    }
}
