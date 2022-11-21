package team017.comments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team017.comments.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findByBoard_BoardId(Long boardId, Pageable pageable);
}
