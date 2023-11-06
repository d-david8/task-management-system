package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    String deleteUserById(Long id);

    UserDTO updateUserById(Long id, UserDTO newUserDTO);
}