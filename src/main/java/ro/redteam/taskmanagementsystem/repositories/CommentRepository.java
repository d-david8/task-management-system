package ro.redteam.taskmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.redteam.taskmanagementsystem.models.entities.Comment;
import ro.redteam.taskmanagementsystem.models.entities.Task;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
