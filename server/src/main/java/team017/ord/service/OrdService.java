package team017.ord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.board.Entity.Board;
import team017.board.Repository.BoardRepository;
import team017.board.Service.BoardService;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Client;
import team017.member.service.ClientService;
import team017.ord.dto.OrdPostDto;
import team017.ord.dto.OrdResponseDto;
import team017.ord.entity.Ord;
import team017.ord.mapper.OrdMapper;
import team017.ord.repository.OrdRepository;
import team017.product.Service.ProductService;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrdService {
    private final OrdRepository ordRepository;
    private final OrdMapper ordMapper;
    private final ProductService productService;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final ClientService clientService;

    //주문은 Client 만 할 수 있고, 판매자는 내역만 조회로 가져간다
    public OrdResponseDto createOrd(Ord ord, OrdPostDto ordPostDto) {

        //판매자 존재 여부 확인
        Client findClient = clientService.findClient(ordPostDto.getClientId());

        //게시글 존재 여부 확인
        Board findBoard = boardService.findVerifiedBoard(ordPostDto.getBoardId());

        //상품 존재 여부 확인
        productService.findProduct(findBoard.getProduct().getProductId());

        //ord DB 저장
        ord.setProduct(productService.findProduct(findBoard.getBoardId()));
        ord.setClient(clientService.findVerifiedClient(findClient.getClientId()));
        ordRepository.save(ord);

        //주문 등록 시, 재고에서 수량만큼 빼기
        findBoard.setLeftStock(findBoard.getLeftStock() - ord.getQuantity());
        boardRepository.save(findBoard);

        OrdResponseDto responseDto = ordMapper.ordToOrdResponseDto(ord);

        return responseDto;
    }

    public void deleteOrd(Long ordId){
        Ord foundOrd = findVerifiedOrd(ordId);
        ordRepository.delete(foundOrd);
    }

    public Ord findVerifiedOrd(Long ordId){
        Optional<Ord> optionalOrd = ordRepository.findById(ordId);
        Ord findOrd = optionalOrd.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrd;
    }
}
