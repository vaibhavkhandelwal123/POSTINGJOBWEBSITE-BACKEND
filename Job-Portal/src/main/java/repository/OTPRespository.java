package repository;

import entity.OTP;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OTPRespository extends MongoRepository<OTP,String> {
}
