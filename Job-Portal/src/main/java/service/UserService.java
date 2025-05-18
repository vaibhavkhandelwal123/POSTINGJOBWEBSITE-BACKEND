package service;

import dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
}
