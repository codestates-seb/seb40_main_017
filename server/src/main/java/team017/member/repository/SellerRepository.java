package team017.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team017.member.entity.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
}
