package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.exceptions.DataExistsException;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.exceptions.UserNotFoundException;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getEmail().isEmpty() || userDTO.getFirstName().isEmpty() || userDTO.getLastName().isEmpty()) {
            throw new EmptyInputException("Filed empty");
        }

        try {
            userDTO.setTaskId(null);
            User userEntity = objectMapper.convertValue(userDTO, User.class);
            User userResponseEntity = userRepository.save(userEntity);
            return objectMapper.convertValue(userResponseEntity, UserDTO.class);
        } catch (DataIntegrityViolationException e) {
            throw new DataExistsException("Invalid email");
        }
    }

    @Override
    public UserDTO getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return objectMapper.convertValue(user, UserDTO.class);
        } else {
            throw new UserNotFoundException("User does not exist!");
        }
    }


    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .toList();
    }

    @Override
    public String deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User deleted successfully!";
        } else {
            throw new UserNotFoundException("User does not exist!");
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
                userRepository.save(user);
                return objectMapper.convertValue(user, UserDTO.class);
            } catch (DataIntegrityViolationException e) {
                throw new EmailExistsException("Invalid email");
            }
        } else {
            throw new UserNotFoundException("User does not exist!");
        }
    }
}