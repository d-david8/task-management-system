package ro.redteam.taskmanagementsystem.models.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Long currentTask;
    private List<CommentUserResponseDTO> comments;
}
