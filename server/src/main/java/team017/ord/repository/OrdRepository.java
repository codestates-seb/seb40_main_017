package team017.ord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team017.ord.entity.Ord;

public interface OrdRepository  extends JpaRepository<Ord,Long> {
}
