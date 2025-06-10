package service;

import dto.LoginDTO;
import dto.UserDTO;
import exception.JobPortalException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO) throws Exception;
    List<UserDTO> getAllUsers();
    void delete(String email);
    public UserDTO loginUser(LoginDTO loginDTO);
    public UserDTO forgotUser(LoginDTO loginDTO);

    public Boolean sendOtp(String email) throws Exception;
}
