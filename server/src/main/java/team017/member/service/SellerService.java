package team017.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
}
