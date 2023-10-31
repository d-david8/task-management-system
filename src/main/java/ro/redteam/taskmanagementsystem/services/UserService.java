package ro.redteam.taskmanagementsystem.services;

import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
}