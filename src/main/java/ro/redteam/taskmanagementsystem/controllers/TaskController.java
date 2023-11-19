package ro.redteam.taskmanagementsystem.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskResponseDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UpdateProgressRequestDTO;
import ro.redteam.taskmanagementsystem.services.TaskService;

import java.util.Date;
import java.util.List;

@Validated
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/byDueDate")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByDueDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dueDate) {
        return ResponseEntity.ok(taskService.getTasksByDueDate(dueDate));
    }

    @PatchMapping("/{taskId}/{userId}")
    public ResponseEntity<TaskResponseDTO> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        return ResponseEntity.ok(taskService.assignTaskById(taskId, userId));
    }

    @PutMapping("/updateProgress")
    public ResponseEntity<TaskResponseDTO> updateProgress(@RequestBody UpdateProgressRequestDTO updateProgressRequestDTO) {
        return ResponseEntity.ok(taskService.updateProgress(updateProgressRequestDTO));
    }

    @PutMapping("/updateToDone/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateProgress(@PathVariable Long taskId) {
        return ResponseEntity.ok(taskService.updateTaskToDone(taskId));
    }

    @GetMapping("/getCompletionSummary")
    public ResponseEntity<String> getCompletionSummary(@RequestParam  Long taskId) {
        return ResponseEntity.ok(taskService.getCompletionSummary(taskId));
    }

    @GetMapping("/getProgressSummary")
    public ResponseEntity<String> getProgressSummary(@RequestParam  Long taskId) {
        return ResponseEntity.ok(taskService.getProgressSummary(taskId));
    }



}