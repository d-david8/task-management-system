package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.enums.Status;
import ro.redteam.taskmanagementsystem.exceptions.*;
import ro.redteam.taskmanagementsystem.models.dtos.CommentTaskResponseDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskResponseDTO;
import ro.redteam.taskmanagementsystem.models.entities.Task;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.TaskRepository;
import ro.redteam.taskmanagementsystem.repositories.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
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

    public TaskResponseDTO getTaskById(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            return mapTaskToTaskResponseDTO(task);
        } else {
            throw new DataNotFoundException("Task does not exist");
        }
    }

    @Override
    public List<TaskResponseDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::mapTaskToTaskResponseDTO).toList();
    }

    public List<TaskDTO> getTasksByDueDate(Date dueDate) {
        List<Task> tasksByDueDate = taskRepository.getTasksByDueDate(dueDate);
        if (!tasksByDueDate.isEmpty()) {
            return tasksByDueDate.stream().map(task -> objectMapper.convertValue(task, TaskDTO.class)).toList();
        } else {
            throw new NoTaskFoundException("No task with this due date exists!");
        }
    }

    public TaskResponseDTO assignTaskById(Long taskId, Long userId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new NoTaskFoundException("Invalid task id!");
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Invalid user id!");
        }
        try {
            taskRepository.updateTaskUserIdToNull(userId);

            Task taskEntity = taskOptional.get();
            User newUser = new User();
            newUser.setId(userId);
            taskEntity.setUser(newUser);

            return mapTaskToTaskResponseDTO(taskRepository.save(taskEntity));

        } catch (DataIntegrityViolationException exception) {
            throw new DataNotFoundException("Invalid data!");
        }
    }

    private TaskResponseDTO mapTaskToTaskResponseDTO(Task task) {
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setProgress(task.getProgress());
        taskResponseDTO.setStatus(task.getStatus());
        taskResponseDTO.setDescription(task.getDescription());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setPriority(task.getPriority());
        taskResponseDTO.setDueDate(task.getDueDate());
        taskResponseDTO.setUserId(task.getUser() == null ? null : task.getUser().getId());

        taskResponseDTO.setComments(task.getComments().stream().map(comment -> {
            CommentTaskResponseDTO commentTaskResponseDTO = new CommentTaskResponseDTO();
            commentTaskResponseDTO.setId(comment.getId());
            commentTaskResponseDTO.setCreatedAt(comment.getCreatedAt());
            commentTaskResponseDTO.setMessage(comment.getMessage());
            commentTaskResponseDTO.setUserId(comment.getUser().getId());
            return commentTaskResponseDTO;
        }).toList());
        return taskResponseDTO;
    }
}