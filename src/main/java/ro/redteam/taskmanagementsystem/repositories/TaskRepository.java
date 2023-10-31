package ro.redteam.taskmanagementsystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.redteam.taskmanagementsystem.models.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}