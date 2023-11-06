package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.exceptions.InvalidUserIdOrTaskIdException;
import ro.redteam.taskmanagementsystem.models.dtos.CommentDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.entities.Comment;
import ro.redteam.taskmanagementsystem.models.entities.Task;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.CommentRepository;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    private final ObjectMapper objectMapper;
    private final CommentRepository commentRepository;

    public CommentServiceImpl(ObjectMapper objectMapper, CommentRepository commentRepository) {
        this.objectMapper = objectMapper;
        this.commentRepository = commentRepository;
    }


    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        try {
            Comment commentEntity = new Comment();

            commentEntity.setMessage(commentDTO.getMessage());

            User user = new User();
            user.setId(commentDTO.getUser().getId());

            Task task = new Task();
            task.setId(commentDTO.getTask().getId());

            commentEntity.setUser(user);
            commentEntity.setTask(task);

            commentEntity.setCreatedAt(LocalDateTime.now());

            Comment responseEntity = commentRepository.save(commentEntity);

            CommentDTO responseCommentDTO = new CommentDTO();
            responseCommentDTO.setMessage(responseEntity.getMessage());
            responseCommentDTO.setId(responseEntity.getId());
            responseCommentDTO.setCreatedAt(responseEntity.getCreatedAt());
            responseCommentDTO.setUser(objectMapper.convertValue(responseEntity.getUser(), UserDTO.class));
            responseCommentDTO.setTask(objectMapper.convertValue(responseEntity.getTask(), TaskDTO.class));

            return responseCommentDTO;

        } catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new InvalidUserIdOrTaskIdException("Task id or user id is invalid");
        }
    }
}