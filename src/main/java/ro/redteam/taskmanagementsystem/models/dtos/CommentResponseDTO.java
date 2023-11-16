package ro.redteam.taskmanagementsystem.models.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponseDTO {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private Long userId;
    private Long taskId;
}