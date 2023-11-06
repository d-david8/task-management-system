package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskResponseDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskResponseDTO getTaskById(Long id);

    List<TaskResponseDTO> getAllTasks();
  
    List<TaskDTO> getTasksByDueDate(Date dueDate);
}