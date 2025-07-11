package service;

import dto.*;
import entity.Applicant;
import entity.Job;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.JobRepository;
import utility.Utilities;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Override
    public void applyJob(Long id, ApplicantDTO applicantDTO) throws JobPortalException {
        Job job = jobRepository.findById(id).orElseThrow(()->new JobPortalException("Job not found"));
        List<Applicant> applicants = job.getApplicants();
        if(applicants==null)applicants=new ArrayList<>();
        if(applicants.stream().filter((x)->x.getApplicantId()==applicantDTO.getApplicantId()).toList().size()>0)throw new JobPortalException("Job Applied already");
        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicants.add(applicantDTO.toEntity());
        job.setApplicants(applicants);
        jobRepository.save(job);
    }

    @Override
    public List<JobDTO> getJobsPostedBy(Long id) throws JobPortalException {
        return jobRepository.findByPostedBy(id)
                .stream()
                .map((x)->x.toDTO())
                .toList();
    }

    @Override
    public void changeAppStatus(ApplicationDTO applicationDTO) {
        Job job = jobRepository.findById(applicationDTO.getId()).orElseThrow(()->new JobPortalException("Job not found"));
        List<Applicant> applicants = job.getApplicants().stream().map((x)->{
            if(applicationDTO.getApplicantId()==x.getApplicantId()){
                x.setApplicationStatus(applicationDTO.getApplicationStatus());
                if(applicationDTO.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING))x.setInterviewTime(applicationDTO.getInterviewTime());
            }
            return x;
        }).toList();
                job.setApplicants(applicants);
                jobRepository.save(job);
    }


}
