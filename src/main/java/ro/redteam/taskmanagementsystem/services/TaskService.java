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

    List<TaskResponseDTO> getTasksByDueDate(Date dueDate);

    TaskResponseDTO assignTaskById(Long taskId, Long userId);

    TaskResponseDTO updateProgress(UpdateProgressRequestDTO updateProgressRequestDTO);

    TaskResponseDTO updateTaskToDone(Long taskId);

    String getCompletionSummary(Long taskId);

    String getProgressSummary(Long taskId);
}