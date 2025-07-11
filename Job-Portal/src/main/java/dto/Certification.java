package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Certification {
    private String title;
    private String issuer;
    private LocalDateTime issueDate;
    private String certificateId;
}
