package service;

import dto.LoginDTO;
import dto.UserDTO;
import exception.JobPortalException;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    List<UserDTO> getAllUsers();
    void delete(String email);
    public UserDTO loginUser(LoginDTO loginDTO);
}
