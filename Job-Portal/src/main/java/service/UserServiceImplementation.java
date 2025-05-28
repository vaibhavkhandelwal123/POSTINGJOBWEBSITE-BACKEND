package service;

import dto.LoginDTO;
import dto.UserDTO;
import entity.User;
import exception.JobPortalException;
import exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import utility.Utilities;

import java.util.List;
@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws Exception {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + userDTO.getEmail());
        }
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toEntity();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::toEntity)
                .toList();
    }

    public void delete(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User with email " + email + " not found");
        }
        userRepository.deleteByEmail(email);
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobPortalException {
        User user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new JobPortalException("User is not registered"));
        if(!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
            throw new JobPortalException("Invalid Credentials");
        }
        return user.toEntity();
    }
}
