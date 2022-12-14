package team017.ord.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.ord.entity.Ord;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdRepository  extends JpaRepository<Ord,Long> {
     Page<Ord> findByClient_ClientId(Long clientId, Pageable pageable);
     List<Ord> findByClient_ClientId(long clientId);
}
