package team017.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import team017.board.Dto.BoardForSellerMyPageDto;
import team017.board.Repository.BoardRepository;
import team017.global.Exception.BusinessLogicException;
import team017.global.Exception.ExceptionCode;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.repository.SellerRepository;
import team017.security.utils.SecurityUtil;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SellerService {
    private final SellerRepository sellerRepository;
    private final BoardRepository boardRepository;
    private final MemberService memberService;

    /* 존재하는 생산자인지 확인 */
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
        correctSeller(sellerId);
        Seller findSeller = findVerifiedSeller(sellerId);
        Optional.ofNullable(seller.getIntroduce()).ifPresent(introduce -> findSeller.setIntroduce(introduce));
        Optional.ofNullable(seller.getImageUrl()).ifPresent(image -> findSeller.setImageUrl(image));

        return sellerRepository.save(findSeller);
    }

    public List<BoardForSellerMyPageDto> getSellerBoard(long sellerId) {
        List<BoardForSellerMyPageDto> sellerBoard = boardRepository.sellerBoard(sellerId);

        if (sellerBoard.size() > 5) {
            sellerBoard = sellerBoard.subList(0, 5);
        }

        return sellerBoard;
    }

    /* 로그인 한 사용자와 접근 사용자 판별 */
    public void correctSeller(long accessId) {
        String email = SecurityUtil.getCurrentEmail();
        Member loginMember = memberService.findMemberByEmail(email);
        log.info("로그인한 유저 : {}", loginMember.getName());

        if (loginMember.getSeller().getSellerId() != accessId) {
            throw new BusinessLogicException(ExceptionCode.WRONG_ACCESS);
        }
    }
}