package api;

import dto.LoginDTO;
import dto.UserDTO;
import exception.JobPortalException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;

@Validated
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserAPI {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws Exception {
        UserDTO savedUser = userService.registerUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws Exception {
        return new ResponseEntity<>(userService.loginUser(loginDTO),HttpStatus.OK);
    }

    @DeleteMapping("/userdel")
    public ResponseEntity<String> deleteUser(@RequestParam String email){
        try {
            userService.delete(email);
            return ResponseEntity.ok("User with email " + email + " deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<UserDTO> forgotUser(@RequestBody LoginDTO code){
        return new ResponseEntity<>(userService.forgotUser(code),HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from UserAPI!";
    }
}
