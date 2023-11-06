package ro.redteam.taskmanagementsystem.models.dtos;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private Long userId;
    private Long taskId;
}
