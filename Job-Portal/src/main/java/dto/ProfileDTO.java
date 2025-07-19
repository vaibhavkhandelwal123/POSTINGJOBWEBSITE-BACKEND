package dto;

import entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {

    private Long id;
    private String name;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String pictures;
    private Long totalExp;
    private List<String> skills;
    private List<Experience>experiences;
    private List<Certification>certifications;
    private List<Long>savedJobs;
    public Profile toEntity(){
        return new Profile(this.id,this.name,this.email,this.jobTitle,this.company,this.location,this.about,this.pictures!=null? Base64.getDecoder().decode(this.pictures):null,this.totalExp,this.skills,this.experiences,this.certifications,this.savedJobs);
    }
}
