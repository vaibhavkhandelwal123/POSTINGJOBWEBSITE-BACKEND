package api;

import dto.*;
import exception.JobPortalException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.html.HTMLTableCaptionElement;
import service.JobService;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/jobs")
public class JobAPI {
    @Autowired
    private JobService jobService;

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postJob(@RequestBody @Valid JobDTO jobDTO) throws Exception {
        return new ResponseEntity<>(jobService.postJob(jobDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobDTO>>getAllJobs() throws JobPortalException{
        return new ResponseEntity<>(jobService.getJobs(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobDTO>getJob(@PathVariable Long id) throws JobPortalException{
        return new ResponseEntity<>(jobService.getjob(id), HttpStatus.OK);
    }
    @PostMapping("/apply/{id}")
    public ResponseEntity<ResponseDTO> applyJob(@PathVariable Long id,@RequestBody ApplicantDTO applicantDTO) throws JobPortalException {
        jobService.applyJob(id,applicantDTO);
        return new ResponseEntity<>(new ResponseDTO("Applied Successfully"),HttpStatus.OK);
    }

    @GetMapping("/getJobPostedBy/{id}")
    public ResponseEntity<List<JobDTO>>getJobPostedBy(@PathVariable Long id) throws JobPortalException{
        return new ResponseEntity<>(jobService.getJobsPostedBy(id), HttpStatus.OK);
    }

    @PostMapping("/changeAppStatus")
    public ResponseEntity<ResponseDTO> changeAppStatus(@RequestBody ApplicationDTO applicationDTO) throws JobPortalException {
        jobService.changeAppStatus(applicationDTO);
        return new ResponseEntity<>(new ResponseDTO("Application Status Changed Successfully"),HttpStatus.OK);
    }
}
