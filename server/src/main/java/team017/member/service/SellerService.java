package team017.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.board.Dto.BoardForSellerMyPageDto;
import team017.board.Entity.Board;
import team017.board.Repository.BoardRepository;
import team017.member.entity.Seller;
import team017.member.repository.SellerRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final BoardRepository boardRepository;

    /* 존재하는 생산자인지 확인 + 생산자 정보 리턴 */
    public Seller findVerifiedSeller(long sellerId) {
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
        Seller findSeller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller Not Found"));

        return findSeller;
    }

    /* 생산자 조회 */
    @Transactional(readOnly = true)
    public Seller findSeller(long sellerId) {
        Seller seller = findVerifiedSeller(sellerId);

        return seller;
    }

    /* 생산자 정보 수정 */
    public Seller updateSeller(long sellerId, Seller seller) {
        log.info("# 생산자 정보 수정 서비스 시작!");
        Seller findSeller = findVerifiedSeller(sellerId);
        Optional.ofNullable(seller.getIntroduce()).ifPresent(introduce -> findSeller.setIntroduce(introduce));
        Optional.ofNullable(seller.getImageUrl()).ifPresent(image -> findSeller.setImageUrl(image));

        log.info("# 생산자 정보 수정 서비스 저장 직전!");
        return sellerRepository.save(findSeller);
    }

    public List<BoardForSellerMyPageDto> getSellerBoard(long sellerId) {
        List<BoardForSellerMyPageDto> sellerBoard = boardRepository.sellerBoard(sellerId);

        return sellerBoard;
    }
}

