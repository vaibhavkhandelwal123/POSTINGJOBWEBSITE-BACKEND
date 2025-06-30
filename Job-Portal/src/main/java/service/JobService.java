package service;

import dto.JobDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface JobService {
    public JobDTO postJob(JobDTO jobDTO) throws Exception;

    public List<JobDTO> getJobs();

    public JobDTO getjob(Long id);
}
