package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;

import java.util.Date;
import java.util.List;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    List<TaskDTO> getTasksByDueDate(Date dueDate);
}