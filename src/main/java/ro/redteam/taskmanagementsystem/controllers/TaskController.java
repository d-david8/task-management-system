package ro.redteam.taskmanagementsystem.controllers;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.services.TaskService;

import java.util.Date;
import java.util.List;

@Validated
@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDTO>> getTasksByDueDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dueDate) {
        return ResponseEntity.ok(taskService.getTasksByDueDate(dueDate));
    }
}