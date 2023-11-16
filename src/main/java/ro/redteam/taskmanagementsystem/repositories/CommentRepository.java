package ro.redteam.taskmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.redteam.taskmanagementsystem.models.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}