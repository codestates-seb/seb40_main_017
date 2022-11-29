package team017.ord.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import team017.product.Entity.Product;
import team017.product.Repository.ProductRepository;
import team017.product.Service.ProductService;

import javax.transaction.Transactional;
import java.util.Optional;
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class OrdService {
    private final OrdRepository ordRepository;
    private final OrdMapper ordMapper;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final ClientService clientService;

    //주문은 Client 만 할 수 있고, 판매자는 내역만 조회로 가져간다
    public OrdResponseDto createOrd(OrdPostDto ordPostDto) {

        //판매자 존재 여부 확인
        Client findClient = clientService.findClient(ordPostDto.getClientId());

        //게시글 존재 여부 확인
        Board findBoard = boardService.findVerifiedBoard(ordPostDto.getBoardId());

        //상품 존재 여부 확인
        Product findProduct = productService.findProduct(findBoard.getProduct().getProductId());

        //주문 시 재고가 없다면
        if(findProduct.getLeftStock() == 0){

            System.out.print("현재 상태:");
            System.out.print(findProduct.getStatus());
            System.out.println();
            System.out.print(findProduct.getMainImage());
            System.out.println();
            System.out.print(findProduct.getPrice());
            System.out.println();

            findProduct.setStatus(Product.ProductStatus.PRD_SOLDOUT);
            findProduct.setMainImage("왜 안바뀌는가.png");
            findProduct.setPrice(11111);
            productRepository.saveAndFlush(findProduct);

            System.out.println();
            System.out.print("현재 상태:");
            System.out.print(findProduct.getStatus());
            System.out.println();
            System.out.print(findProduct.getMainImage());
            System.out.println();
            System.out.print(findProduct.getPrice());
            System.out.println();

            throw new BusinessLogicException(ExceptionCode.PRODUCT_SOLDOUT);
        }
        //재고가 있다면
        else{

            Ord ord = ordMapper.ordPostDtoToOrd(ordPostDto);

            //재고 < 수량
            if(findProduct.getLeftStock() < ord.getQuantity()){
                throw new BusinessLogicException(ExceptionCode.PRODUCT_NOT_ENOUGH);
            }
            //재고 > 수량
            //주문 등록 시, 재고에서 수량만큼 빼기
            findProduct.setLeftStock(findProduct.getLeftStock() - ord.getQuantity());
            productRepository.save(findProduct);

            //ord DB 저장
            ord.setProduct(productService.findProduct(findBoard.getBoardId()));
            ord.setClient(clientService.findVerifiedClient(findClient.getClientId()));
            ordRepository.save(ord);
            OrdResponseDto responseDto = ordMapper.ordToOrdResponseDto(ord);

            return responseDto;

        }

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

    public Page<Ord> findClientOrd(Long clientId, int page, int size){
        return ordRepository.findByClient_ClientId(clientId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    public Page<Board> findSellerOrd(Long sellerId, int page, int size){
        return boardRepository.findBySeller_SellerId(sellerId, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }
}
