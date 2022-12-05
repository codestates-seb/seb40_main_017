package team017.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team017.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	/* 존재하는 이메일인지 확인해야 하기 때문 */
	Optional<Member> findByEmail(String email);

	Member findMemberByEmail(String email);
}
