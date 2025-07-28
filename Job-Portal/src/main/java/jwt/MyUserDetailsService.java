package jwt;

import dto.UserDTO;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import service.UserService;

import java.util.ArrayList;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserDTO dto = userService.getUserByEmail(email);
            return new CustomUserDetails(
                    dto.getId(),
                    email,
                    dto.getName(),
                    dto.getPassword(),
                    dto.getProfileId(),
                    dto.getAccountType(),
                    new ArrayList<>()
            );
        } catch (JobPortalException e) {
            throw new UsernameNotFoundException("User not found: " + email, e);
        }
    }
}
