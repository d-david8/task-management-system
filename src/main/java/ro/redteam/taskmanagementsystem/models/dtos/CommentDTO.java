package ro.redteam.taskmanagementsystem.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private UserDTO user;
    private TaskDTO task;
}