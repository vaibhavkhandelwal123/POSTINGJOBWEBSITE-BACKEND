package service;

import dto.LoginDTO;
import dto.NotificationDTO;
import dto.ResponseDTO;
import dto.UserDTO;
import entity.OTP;
import entity.User;
import exception.JobPortalException;
import exception.UserAlreadyExistsException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.NotificationRepository;
import repository.OTPRespository;
import repository.UserRepository;
import utility.Data;
import utility.Utilities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private OTPRespository otpRespository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private ProfileService profileService;
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
        userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));
        userDTO.setId(Utilities.getNextSequence("users"));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userDTO.toEntity();
        user = userRepository.save(user);
        return user.toDTO();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(User::toDTO)
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
        return user.toDTO();
    }

    @Override
    public ResponseDTO forgotUser(LoginDTO loginDTO) throws Exception {
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobPortalException("User is not registered"));
        if(loginDTO.getPassword().trim().isEmpty()){
            throw new JobPortalException("password field is empty");
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!+=%-*?&#])[A-Za-z\\d@$!+=%-*?&#]{8,}$";
        boolean flag = Pattern.matches(regex,loginDTO.getPassword());
        if(!flag){
            throw new JobPortalException("password is not Strong...");
        }

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        NotificationDTO noti= new NotificationDTO();
        noti.setUserId(user.getId());
        noti.setMessage("Password Reset Successfully");
        noti.setAction("Password Reset");
        notificationService.sendNotification(noti);
        return new ResponseDTO("Password changed successfully");
    }

    @Override
    public UserDTO getUserByEmail(String email) throws JobPortalException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("User not found")).toDTO();
    }

    @Override
    public Boolean sendOtp(String email) throws Exception {
        userRepository.findByEmail(email)
                .orElseThrow(() -> new JobPortalException("User is not registered"));

        String genOtp = Utilities.generateOTP();
        OTP otp = new OTP(email, genOtp, LocalDateTime.now());
        otpRespository.save(otp);

        String htmlContent = Data.buildOtpEmail(email,genOtp);

        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true, "UTF-8");
        message.setTo(email);
        message.setSubject("Your OTP Code for Secure Access");
        message.setText(htmlContent, true);

        javaMailSender.send(mm);
        return true;
    }

    @Override
    public Boolean verifyOtp(String email,String otp) {
        OTP otpEntity = otpRespository.findById(email).orElseThrow(()->new JobPortalException("OTP is expired"));
        if(!otpEntity.getOtpCode().equals(otp)){
            throw new JobPortalException("OTP is incorrect");
        }
        return true;
    }

    @Scheduled(fixedRate = 60000)
    public void removeExpiredOTPs(){
        LocalDateTime exp = LocalDateTime.now().minusMinutes(5);
        List<OTP> expiredOtps=otpRespository.findByCreationTimeBefore(exp);
        if(!expiredOtps.isEmpty()){
            otpRespository.deleteAll(expiredOtps);
            System.out.println("Removed "+expiredOtps.size()+" expired OTPs.");
        }
    }

}
