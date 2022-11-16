package team017.comments.mapper;

import org.mapstruct.Mapper;
import team017.board.Entity.Board;
import team017.comments.dto.CommentPatchDto;
import team017.comments.dto.CommentPostDto;
import team017.comments.dto.CommentResponseDto;
import team017.comments.entity.Comment;
import team017.member.entity.Member;

import java.util.List;


@Mapper(componentModel = "spring")
public interface CommentMapper {
     default Comment commentPostDtoToComment(CommentPostDto commentPostDto) {
          if ( commentPostDto == null ) {
               return null;
          }
          Member member = new Member();
          member.setMemberId(commentPostDto.getMemberId());

          Board board = new Board();
          board.setBoardId(commentPostDto.getBoardId());

          Comment comment = new Comment();
          comment.setMember(member);
          comment.setBoard(board);
          comment.setContext(commentPostDto.getContext());

          return comment;
     }

     default Comment commentPatchDtoToComment(CommentPatchDto commentPatchDto) {
          if ( commentPatchDto == null ) {
               return null;
          }

          Member member = new Member();
          member.setMemberId(commentPatchDto.getMemberId());

          Board board = new Board();
          board.setBoardId(commentPatchDto.getBoardId());

          Comment comment = new Comment();
          comment.setMember(member);
          comment.setBoard(board);
          comment.setCommentId( commentPatchDto.getCommentId());
          comment.setContext( commentPatchDto.getContext());

          return comment;
     }

     default CommentResponseDto commentToCommentResponseDto(Comment comment) {
          if ( comment == null ) {
               return null;
          }

          Member member = new Member();
          member.setMemberId(comment.getMember().getMemberId());

          comment.setCommentId(comment.getCommentId());

          Board board = new Board();
          board.setBoardId(comment.getBoard().getBoardId());

          CommentResponseDto commentResponseDto = new CommentResponseDto();
          commentResponseDto.setMemberId(member.getMemberId());
          commentResponseDto.setCommentId(comment.getCommentId());
          commentResponseDto.setBoardId(board.getBoardId());
          commentResponseDto.setContext(comment.getContext());
          commentResponseDto.setName(comment.getCommentMemberName());
          commentResponseDto.setCreatedAt(comment.getCreatedAt());
          commentResponseDto.setModifiedAt(comment.getModifiedAt());

          return commentResponseDto;
     }

     List<CommentResponseDto> commentToCommentResponseDtos(List<Comment> comments);
}
