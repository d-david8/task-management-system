package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.exceptions.DataExistsException;
import ro.redteam.taskmanagementsystem.exceptions.DataNotFoundException;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.models.dtos.CommentUserResponseDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UserResponseDTO;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.TaskRepository;
import ro.redteam.taskmanagementsystem.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getEmail().isEmpty() || userDTO.getFirstName().isEmpty() || userDTO.getLastName().isEmpty()) {
            throw new EmptyInputException("Filed empty");
        }

        try {
            User userEntity = objectMapper.convertValue(userDTO, User.class);
            User userResponseEntity = userRepository.save(userEntity);
            return objectMapper.convertValue(userResponseEntity, UserDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataExistsException("Invalid email");
        }
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return mapUserToUserResponseDTO(user);
        } else {
            throw new DataNotFoundException("User does not exist!");
        }
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUserToUserResponseDTO).toList();
    }

    @Override
    public String deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            taskRepository.updateTaskUserIdToNull(id);
            userRepository.deleteById(id);
            return "User deleted successfully!";
        } else {
            throw new DataNotFoundException("User does not exist!");
        }
    }

    @Override
    public UserDTO updateUserById(Long id, UserDTO newUserDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                if (!newUserDTO.getFirstName().isEmpty()) {
                    user.setFirstName(newUserDTO.getFirstName());
                }
                if (!newUserDTO.getLastName().isEmpty()) {
                    user.setLastName(newUserDTO.getLastName());
                }
                if (!newUserDTO.getEmail().isEmpty()) {
                    user.setEmail(newUserDTO.getEmail());
                }
                User userResponseEntity = userRepository.save(user);
                UserDTO userResponse = new UserDTO();
                userResponse.setId(userResponseEntity.getId());
                userResponse.setFirstName(userResponseEntity.getFirstName());
                userResponse.setLastName(userResponseEntity.getLastName());
                userResponse.setEmail(userResponseEntity.getEmail());
                return userResponse;
            } catch (DataIntegrityViolationException e) {
                throw new DataExistsException("Invalid email");
            }
        } else {
            throw new DataNotFoundException("User does not exist!");
        }
    }

    private UserResponseDTO mapUserToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCurrentTask(user.getTask() == null ? null : user.getTask().getId());

        userResponseDTO.setComments(user.getComments().stream()
                .map(comment -> {
                    CommentUserResponseDTO commentUserResponseDTO = new CommentUserResponseDTO();
                    commentUserResponseDTO.setId(comment.getId());
                    commentUserResponseDTO.setMessage(comment.getMessage());
                    commentUserResponseDTO.setCreatedAt(comment.getCreatedAt());
                    commentUserResponseDTO.setCreatedAt(comment.getCreatedAt());
                    commentUserResponseDTO.setTaskId(comment.getTask().getId());
                    return commentUserResponseDTO;
                }).toList());
        return userResponseDTO;
    }
}