package service;

import dto.UserDTO;
import exception.JobPortalException;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    List<UserDTO> getAllUsers();
    void delete(String email);
}
