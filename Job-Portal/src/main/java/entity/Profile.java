package entity;

import dto.Certification;
import dto.Experience;
import dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profile")
public class Profile {
    @Id
    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private byte[] pictures;
    private Long totalExp;
    private List<String>skills;
    private List<Experience>experiences;
    private List<Certification>certifications;
    private List<Long>savedJobs;
    public ProfileDTO toDTO(){
        return new ProfileDTO(this.id,this.name,this.email,this.jobTitle,this.company,this.location,this.about,this.pictures!=null? Base64.getEncoder().encodeToString(this.pictures):null,this.totalExp,this.skills,this.experiences,this.certifications,this.savedJobs);
    }

}
