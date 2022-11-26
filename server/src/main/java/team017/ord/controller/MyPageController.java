package team017.ord.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team017.board.Entity.Board;
import team017.board.Mapper.BoardMapper;
import team017.global.response.MultiResponseDto;
import team017.member.service.ClientService;
import team017.member.service.MemberService;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.service.OrdService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {
    private final OrdMapper ordMapper;

    private final OrdService ordService;

    private final BoardMapper boardMapper;


    @GetMapping("/sold/{seller-id}")
    public ResponseEntity getSellerORd(@PathVariable("seller-id") @Positive Long sellerId,
                                       @Positive @RequestParam int page,
                                       @Positive @RequestParam int size){

        Page<Board> sellerPage = ordService.findSellerOrd(sellerId, page-1, size);

        List<Board> sellerPageList = sellerPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(boardMapper.ordToOrdSellerResponseDtos(sellerPageList), sellerPage), HttpStatus.OK);
    }

    @GetMapping("/{client-id}")
    public ResponseEntity getClientOrd(@PathVariable("client-id") @Positive Long clientId,
                                       @Positive @RequestParam int page,
                                       @Positive @RequestParam int size){

        Page<Ord> clientPage = ordService.findClientOrd(clientId, page -1, size);

        List<Ord> clientPageList = clientPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(ordMapper.ordToOrdClientResponseDtos(clientPageList), clientPage), HttpStatus.OK);
    }
}
