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
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String pictures;
    private List<String> skills;
    private List<Experience>experiences;
    private List<Certification>certifications;
    public Profile toEntity(){
        return new Profile(this.id,this.email,this.jobTitle,this.company,this.location,this.about,this.pictures!=null? Base64.getDecoder().decode(this.pictures):null,this.skills,this.experiences,this.certifications);
    }
}
