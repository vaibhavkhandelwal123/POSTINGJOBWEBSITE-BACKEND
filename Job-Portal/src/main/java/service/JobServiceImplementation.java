package service;

import dto.JobDTO;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.JobRepository;
import utility.Utilities;

import java.time.LocalDateTime;
import java.util.List;

@Service("jobService")
public class JobServiceImplementation implements JobService {
    @Autowired
    private JobRepository jobRepository;

    @Override
    public JobDTO postJob(JobDTO jobDTO) throws Exception {
        jobDTO.setId(Utilities.getNextSequence("jobs"));
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
    }

    @Override
    public List<JobDTO> getJobs() {
        return jobRepository.findAll()
                .stream()
                .map((x)->x.toDTO())
                .toList();
    }

    @Override
    public JobDTO getjob(Long id) {
        return jobRepository.findById(id).orElseThrow(()->new JobPortalException("Job not found")).toDTO();
    }


}
