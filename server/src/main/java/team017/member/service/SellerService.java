package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import team017.member.entity.Member;
import team017.member.entity.Seller;
import team017.member.repository.SellerRepository;

@Service
@RequiredArgsConstructor
public class SellerService {
	private final SellerRepository sellerRepository;

	/* 존재하는 생산자인지 확인 + 생산자 정보 리턴 */
	public Seller findVerifiedSeller(long sellerId) {
		Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
		Seller findSeller = optionalSeller.orElseThrow(() -> new RuntimeException("Seller Not Found"));

		return findSeller;
	}

	/* 생산자 정보 수정 */
	public Seller updateSeller(Seller seller) {
		Seller findSeller = findVerifiedSeller(seller.getSellerId());
		Optional.ofNullable(seller.getIntroduce()).ifPresent(introduce -> findSeller.setIntroduce(introduce));

		return sellerRepository.save(findSeller);
	}
}
