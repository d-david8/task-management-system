package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;

import java.util.List;

public interface TaskService {

    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    List<TaskDTO> getAllTasks();
}