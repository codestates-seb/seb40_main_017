package team017.ord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team017.board.Service.BoardService;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Client;
import team017.member.service.ClientService;
import team017.member.service.SellerService;
import team017.ord.entity.Ord;
import team017.ord.repository.OrdRepository;
import team017.product.Entity.Product;
import team017.product.Service.ProductService;
import team017.review.entity.Review;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrdService {
    private final OrdRepository ordRepository;

    private final ProductService productService;

    private final ClientService clientService;

    private final BoardService boardService;

    //    주문은 Client 만 할 수 있고, 판매자는 내역만 조회로 가져간다
    public Ord createOrd(Ord ord, Long clientId, long boardId) {
        ord.setClient(clientService.findVerifiedClient(clientId));
        Product product = productService.findProduct(boardId);
        ord.setProduct(product);
        ord.setSeller(product.getBoard().getSeller());
        verifiedClient(ord);
        return ordRepository.save(ord);
    }


    public Ord findOrd(Long ordId){
        Ord findOrd = findVerifiedOrd(ordId);
        return findOrd;
    }

    //주문취소
    public void deleteOrd(Long ordId, Long clientId){
        Ord foundOrd = findOrd(ordId);
        Long postClientId = foundOrd.getClient().getClientId();
        verifyWriter(postClientId, clientId);
        foundOrd.setStatus(Ord.OrdStatus.ORD_CANCEL);
        ordRepository.save(foundOrd);
    }

    public void verifyWriter(Long postUserId, Long editUserId) {
        if (!postUserId.equals(editUserId)) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }
    private void verifiedClient(Ord ord) {
        clientService.findClient(ord.getClient().getClientId());
    }

    public Ord findVerifiedOrd(Long ordId){
        Optional<Ord> optionalOrd = ordRepository.findById(ordId);
        Ord findOrd = optionalOrd.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));
        return findOrd;
    }

    private void verifiedProduct(Ord ord){
        productService.findVerifiedProduct(ord.getProduct().getBoard().getProduct()).getProductId();
    }

    private void verifiedBoard(Review review) {
        boardService.findVerifiedBoard(review.getBoard().getBoardId());
    }
}
