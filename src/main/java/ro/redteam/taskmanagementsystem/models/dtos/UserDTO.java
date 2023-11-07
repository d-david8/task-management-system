package ro.redteam.taskmanagementsystem.models.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private TaskDTO taskID;
    private List<CommentDTO> comments;
}