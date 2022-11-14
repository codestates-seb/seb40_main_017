package team017.comments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Service.BoardService;
import team017.comments.entity.Comment;
import team017.comments.repository.CommentRepository;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.MemberService;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public Comment createComment(Comment comment, Long memberId){
        comment.setMember(memberService.findVerifiedMember(memberId));
        verifiedMember(comment);
        comment.setCommentUsername();


    }
    private void verifyWriter(Long postUserId, Long editUserId){
        if(!postUserId.equals(editUserId)){
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }

    private void verifiedMember(Comment comment){
        memberService.findVerifiedMember(comment.getMember().getMemberId());
    }

    private void verifiedBoard(Comment comment){
        //boardService.
    }
}
