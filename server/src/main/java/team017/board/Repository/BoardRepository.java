package team017.board.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.board.Entity.Board;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}

