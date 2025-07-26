package service;

import dto.LoginDTO;
import dto.ResponseDTO;
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
    public ResponseDTO forgotUser(LoginDTO loginDTO) throws Exception;
    public UserDTO getUserByEmail(String email) throws JobPortalException;
    public Boolean sendOtp(String email) throws Exception;

    public Boolean verifyOtp(String email,String otp);
}
