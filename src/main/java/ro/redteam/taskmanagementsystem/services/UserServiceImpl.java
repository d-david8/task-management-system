package ro.redteam.taskmanagementsystem.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ro.redteam.taskmanagementsystem.exceptions.DataExistsException;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.UserRepository;

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
}