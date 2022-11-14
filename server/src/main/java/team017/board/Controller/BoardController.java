package team017.board.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team017.board.Dto.BoardPatchDto;
import team017.board.Dto.BoardPostDto;
import team017.board.Dto.BoardResponseDto;
import team017.board.Entity.Board;
import team017.board.Service.BoardService;


@RequestMapping("/boards")
@RestController
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시판 등록
    @PostMapping()
    public ResponseEntity postBoard(@RequestBody BoardPostDto boardPostDto){

        BoardResponseDto response = boardService.createBoard(boardPostDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //게시판 수정
    @PatchMapping("/{board_id}")
    public ResponseEntity patchBoard(   @PathVariable("board_id") long boardId,
                                        @RequestBody BoardPatchDto boardPatchDto){

        return new ResponseEntity<>(HttpStatus.OK);
    }


    //게시글 삭제
    @DeleteMapping("/{board_id}")
    public ResponseEntity deleteBoard(){

        return new ResponseEntity<>(HttpStatus.OK);
    }
//
//    //특정 상품 조회(상품 페이지)
//    @GetMapping("/{board_id}")
//
//    //전체 상품 조회
//    @GetMapping()





}

