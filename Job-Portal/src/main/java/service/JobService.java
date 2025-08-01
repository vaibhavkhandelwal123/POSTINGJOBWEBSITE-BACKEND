package service;

import dto.ApplicantDTO;
import dto.ApplicationDTO;
import dto.JobDTO;
import dto.ResponseDTO;
import exception.JobPortalException;
import jakarta.validation.Valid;

import java.util.List;

public interface JobService {
    public JobDTO postJob(JobDTO jobDTO) throws Exception;

    public List<JobDTO> getJobs();

    public JobDTO getjob(Long id);

    public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException;

    public List<JobDTO> getJobsPostedBy(Long id) throws JobPortalException;

    public void changeAppStatus(ApplicationDTO applicationDTO);
}
