package ro.redteam.taskmanagementsystem.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ro.redteam.taskmanagementsystem.models.entities.Task;

import java.util.Date;
import java.util.List;

@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM tasks WHERE due_date = :dueDate", nativeQuery = true)
    List<Task> getTasksByDueDate(Date dueDate);

    @Modifying
    @Query(value = "UPDATE tasks set user_id = NULL where user_id = :userId", nativeQuery = true)
    void updateTaskUserIdToNull(Long userId);
}