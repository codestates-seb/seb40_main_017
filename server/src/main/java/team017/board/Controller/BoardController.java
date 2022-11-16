package team017.board.Controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team017.board.Dto.BoardPatchDto;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Dto.BoardTotalResponseDto;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.board.Service.BoardService;
import team017.global.response.MultiResponseDto;

import javax.validation.constraints.Positive;
import java.util.List;

@RequestMapping("/boards")
@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper boardMapper;

    //게시글 등록
    @PostMapping()
    public ResponseEntity postBoard(@RequestBody BoardPostDto boardPostDto){

        BoardResponseDto response = boardService.createBoard(boardPostDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //게시글 수정
    @PatchMapping("/{board_id}")
    public ResponseEntity patchBoard( @PathVariable("board_id") long boardId,
                                      @RequestBody BoardPatchDto boardPatchDto){

        BoardResponseDto response = boardService.updateBoard(boardId, boardPatchDto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //게시글 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity deleteBoard(@PathVariable("board_id") long boardId){

        boardService.deleteBoard(boardId);

        return new ResponseEntity<>("Success",HttpStatus.OK);
    }

    //단일 상품 조회
    //++sellPhotoList
    @GetMapping("/{board_id}")
    public ResponseEntity GetBoard(@PathVariable("board_id") long boardId){

        BoardResponseDto response = boardService.getBoard(boardId);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //카테고리 별 조회
    @GetMapping("/category/{category}")
    public ResponseEntity GetBoardCategory(@PathVariable("category") int category,
                                           @Positive @RequestParam int page,
                                           @Positive @RequestParam int size){

        Page<Board> boardsPage = boardService.findBoardsCategory(category, page- 1, size);
        List<Board> boardList = boardsPage.getContent();
        List<BoardTotalResponseDto> response = boardMapper.productToBoardToalResponseDto(boardList);

        return new ResponseEntity<>(
                new MultiResponseDto<>(response , boardsPage), HttpStatus.OK);
    }

    //전체 상품 조회
    @GetMapping()
    public ResponseEntity GetBoards(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size){

        Page<Board> boardsPage = boardService.findBoards(page- 1, size);
        List<Board> boardList = boardsPage.getContent();
        List<BoardTotalResponseDto> response = boardMapper.productToBoardToalResponseDto(boardList);

        return new ResponseEntity<>(
                new MultiResponseDto<>(response , boardsPage), HttpStatus.OK);
    }

}

