package repository;

import entity.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OTPRespository extends MongoRepository<OTP,String> {

    List<OTP> findByCreationTimeBefore(LocalDateTime exp);
}
