package ro.redteam.taskmanagementsystem.unit_tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.redteam.taskmanagementsystem.exceptions.EmptyInputException;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.entities.User;
import ro.redteam.taskmanagementsystem.repositories.UserRepository;
import ro.redteam.taskmanagementsystem.services.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void testCreateUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setFirstName("John");
        userDTO.setLastName("Doe");
        userDTO.setEmail("john@gmail.com");

        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john@gmail.com");

        User saveUserEntity = new User();
        saveUserEntity.setId(1L);
        saveUserEntity.setFirstName("John");
        saveUserEntity.setLastName("Doe");
        saveUserEntity.setEmail("john@gmail.com");

        when(objectMapper.convertValue(userDTO, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(saveUserEntity);
        when(objectMapper.convertValue(saveUserEntity, UserDTO.class)).thenReturn(userDTO);

        UserDTO saveUserDTO = userService.createUser(userDTO);

        verify(userRepository, times(1)).save(user);
        assertEquals(userDTO, saveUserDTO);
    }

    @Test
    void testCreateUserWithEmptyInputException(){
        UserDTO userDTO = new UserDTO();
        userDTO.setLastName("");
        userDTO.setFirstName("");
        userDTO.setEmail("");

        assertThrows(EmptyInputException.class, ()-> userService.createUser(userDTO));

        verify(userRepository, never()).save(any(User.class));
    }
}