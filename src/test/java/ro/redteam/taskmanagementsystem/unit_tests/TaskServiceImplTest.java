package ro.redteam.taskmanagementsystem.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.entities.Task;
import ro.redteam.taskmanagementsystem.repositories.TaskRepository;
import ro.redteam.taskmanagementsystem.services.TaskServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void testCreateTask() {

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitle("Do something");
        taskDTO.setDescription("Something lse");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Do something");
        task.setDescription("Something lse");

        Task saveTaskEntity = new Task();
        saveTaskEntity.setId(1L);
        saveTaskEntity.setTitle("Do something");
        saveTaskEntity.setDescription("Something lse");

        when(objectMapper.convertValue(taskDTO, Task.class)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(saveTaskEntity);
        when(objectMapper.convertValue(saveTaskEntity, TaskDTO.class)).thenReturn(taskDTO);

        TaskDTO saveTaskDTO = taskService.createTask(taskDTO);

        verify(taskRepository, times(1)).save(task);
        assertEquals(taskDTO, saveTaskDTO);
    }

    @Test
    void testCreateUserWithEmptyInputException(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("");
        taskDTO.setDescription("");

        assertThrows(EmptyInputException.class, ()-> taskService.createTask(taskDTO));

        verify(taskRepository, never()).save(any(Task.class));
    }
}