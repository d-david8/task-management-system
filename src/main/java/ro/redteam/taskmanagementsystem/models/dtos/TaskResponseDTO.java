package ro.redteam.taskmanagementsystem.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ro.redteam.taskmanagementsystem.enums.Priority;
import ro.redteam.taskmanagementsystem.enums.Status;

import java.util.Date;
import java.util.List;

@Data
public class TaskResponseDTO {

    private Long id;
    private String title;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date dueDate;
    private Priority priority;
    private Status status;
    private int progress;
    private Long userId;
    private List<CommentTaskResponseDTO> comments;
}