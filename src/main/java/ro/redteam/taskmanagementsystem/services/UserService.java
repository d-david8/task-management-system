package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;
import ro.redteam.taskmanagementsystem.models.dtos.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserResponseDTO getUserById(Long id);

    List<UserResponseDTO> getAllUsers();

    String deleteUserById(Long id);

    UserDTO updateUserById(Long id, UserDTO newUserDTO);
}