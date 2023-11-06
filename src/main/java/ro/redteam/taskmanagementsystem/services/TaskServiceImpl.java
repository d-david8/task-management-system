package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.enums.Status;
import ro.redteam.taskmanagementsystem.exceptions.DataExistsException;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.exceptions.NoTaskFoundException;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.entities.Task;
import ro.redteam.taskmanagementsystem.repositories.TaskRepository;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ObjectMapper objectMapper;

    public TaskServiceImpl(TaskRepository taskRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (taskDTO.getTitle().isEmpty() || taskDTO.getDescription().isEmpty()) {
            throw new EmptyInputException("Filed empty");
        }

        try {
            taskDTO.setStatus(Status.TODO);
            taskDTO.setProgress(0);
            Task taskEntity = objectMapper.convertValue(taskDTO, Task.class);
            Task taskResponseEntity = taskRepository.save(taskEntity);

            return objectMapper.convertValue(taskResponseEntity, TaskDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataExistsException("Invalid title");
        }
    }

    @Override
    public List<TaskDTO> getTasksByDueDate(Date dueDate) {
        List<Task> tasksByDueDate = taskRepository.getTasksByDueDate(dueDate);
        if (!tasksByDueDate.isEmpty()) {
            return tasksByDueDate.stream()
                    .map(task -> objectMapper.convertValue(task, TaskDTO.class))
                    .toList();
        } else {
            throw new NoTaskFoundException("No task with this due date exists!");
        }
    }
}