package team017.ord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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

    //주문은 Client 만 할 수 있고, 판매자는 내역만 조회로 가져간다
//    public Ord createOrd(Ord ord, Long clientId){
//        ord.setClient(clientService.findVerifiedClient(clientId));
//        verifiedClient(ord);
//    }

//    private Ord verifiedClient(Ord ord){
//        //client 존재하는지 확인
//        clientService.findVerifiedClient(ord.getClient().getClientId());
//
//    }
}
