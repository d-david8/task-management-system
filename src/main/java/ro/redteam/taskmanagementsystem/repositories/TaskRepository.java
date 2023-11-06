package ro.redteam.taskmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.redteam.taskmanagementsystem.models.entities.Task;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM tasks WHERE due_date = :dueDate", nativeQuery = true)
    List<Task> getTasksByDueDate(Date dueDate);
}