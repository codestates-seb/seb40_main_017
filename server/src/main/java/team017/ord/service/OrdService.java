package team017.ord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.service.ClientService;
import team017.member.service.SellerService;
import team017.ord.entity.Ord;
import team017.ord.repository.OrdRepository;
import team017.product.Service.ProductService;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrdService {
    private final OrdRepository ordRepository;

    private final ProductService productService;

    private final ClientService clientService;

    private final SellerService sellerService;

    //    주문은 Client 만 할 수 있고, 판매자는 내역만 조회로 가져간다
    public Ord createOrd(Ord ord, Long clientId) {
        ord.setClient(clientService.findVerifiedClient(clientId));
        return ordRepository.save(ord);
    }

    public Ord findOrd(Long ordId) {
        return findVerifiedOrder(ordId);
    }

//    public Ord

    public void deleteOrd(Ord ord, Long clientId) {
        Ord foundOrd = findOrd(ord.getOrdId());
        Long ordClientId = foundOrd.getClient().getClientId();
        verifiedClient(ordClientId, clientId);
        ordRepository.delete(foundOrd);
    }

    private void verifiedClient(Long ordClientId, Long clientId){
        //주문을 한 Client인지 확인
        if(!ordClientId.equals(clientId)){
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_MEMBER);
        }
    }


    private Ord findVerifiedOrder(Long ordId){
        Optional<Ord> optionalOrd = ordRepository.findById(ordId);
        Ord findOrd = optionalOrd.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return findOrd;
    }

    private void verifiedProduct(Ord ord){
        productService.findVerifiedProduct(ord.getProduct().getBoard().getProduct()).getProductId();
    }
}
