package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.exceptions.InvalidUserIdOrTaskIdException;
import ro.redteam.taskmanagementsystem.models.dtos.CommentDTO;
import ro.redteam.taskmanagementsystem.models.dtos.CommentResponseDTO;
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.entities.Comment;
import ro.redteam.taskmanagementsystem.models.entities.Task;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentResponseDTO addComment(CommentResponseDTO commentResponseDTO) {
        try {
            Comment commentEntity = new Comment();
            User user = new User();
            Task task = new Task();

            user.setId(commentResponseDTO.getUserId());
            task.setId(commentResponseDTO.getTaskId());

            commentEntity.setMessage(commentResponseDTO.getMessage());
            commentEntity.setUser(user);
            commentEntity.setTask(task);
            commentEntity.setCreatedAt(LocalDateTime.now());

            Comment responseEntity = commentRepository.save(commentEntity);

            CommentResponseDTO responseDTO = new CommentResponseDTO();

            responseDTO.setId(responseEntity.getId());
            responseDTO.setMessage(responseEntity.getMessage());
            responseDTO.setCreatedAt(responseEntity.getCreatedAt());
            responseDTO.setUserId(responseEntity.getUser().getId());
            responseDTO.setTaskId(responseEntity.getTask().getId());

            return responseDTO;

        } catch (DataIntegrityViolationException exception) {
            System.out.println(exception.getMessage());
            throw new InvalidUserIdOrTaskIdException("Task id or user id is invalid");
        }
    }

    @Override
    public List<CommentResponseDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapCommentToCommentResponseDTO).toList();
    }

    private CommentResponseDTO mapCommentToCommentResponseDTO(Comment comment) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();

        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setMessage(comment.getMessage());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt());
        commentResponseDTO.setUserId(comment.getUser().getId());
        commentResponseDTO.setTaskId(comment.getTask().getId());
        return commentResponseDTO;
    }
}