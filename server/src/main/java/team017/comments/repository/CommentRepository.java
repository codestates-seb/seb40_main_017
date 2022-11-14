package team017.comments.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team017.comments.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
