package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
}