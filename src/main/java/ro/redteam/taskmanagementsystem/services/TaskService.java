package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskResponseDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UpdateProgressRequestDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskResponseDTO getTaskById(Long id);

    List<TaskResponseDTO> getAllTasks();

    List<TaskDTO> getTasksByDueDate(Date dueDate);

    TaskResponseDTO updateProgress(UpdateProgressRequestDTO updateProgressRequestDTO);

    TaskResponseDTO updateTaskToDone(Long taskId);
}