package team017.comments.mapper;

import org.mapstruct.Mapper;
import team017.board.Entity.Board;
import team017.comments.dto.CommentPatchDto;
import team017.comments.dto.CommentPostDto;
import team017.comments.dto.CommentResponseDto;
import team017.comments.entity.Comment;
import team017.member.entity.Member;


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

          Comment comment = new Comment();

          comment.setCommentId( commentPatchDto.getCommentId() );
          comment.setContext( commentPatchDto.getContext() );

          return comment;
     }

     default CommentResponseDto commentToCommentResponseDto(Comment comment) {
          if ( comment == null ) {
               return null;
          }

          CommentResponseDto commentResponseDto = new CommentResponseDto();

          return commentResponseDto;
     }

}
