package service;

import dto.*;
import entity.Applicant;
import entity.Job;
import entity.Notification;
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

    @Autowired
    private NotificationService notificationService;
    @Override
    public JobDTO postJob(JobDTO jobDTO) throws Exception {
        if(jobDTO.getId()==0){
            jobDTO.setId(Utilities.getNextSequence("jobs"));
            jobDTO.setPostTime(LocalDateTime.now());
            NotificationDTO notiDTO = new NotificationDTO();
            notiDTO.setAction("Job Posted Successfully");
            notiDTO.setMessage("Job Posted Successfully for "+jobDTO.getJobTitle()+" at "+jobDTO.getCompany());
            notiDTO.setUserId(jobDTO.getPostedBy());
            notiDTO.setRoute("/posted-job/"+jobDTO.getId());
            notificationService.sendNotification(notiDTO);
        }
        else{
            jobRepository.findById(jobDTO.getId()).orElseThrow(()->new JobPortalException("Job not found"));
            if(jobDTO.getJobStatus().equals(JobStatus.DRAFT) ||jobDTO.getJobStatus().equals(JobStatus.CLOSED)){
                jobDTO.setPostTime(LocalDateTime.now());
            }
        }
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
            if(applicationDTO.getApplicantId() == x.getApplicantId()){
                x.setApplicationStatus(applicationDTO.getApplicationStatus());
                if(applicationDTO.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)) {
                    x.setInterviewTime(applicationDTO.getInterviewTime());
                    NotificationDTO notiDTO = new NotificationDTO();
                    notiDTO.setAction("Interview Scheduled");
                    notiDTO.setMessage("Interview scheduled for job id "+applicationDTO.getId());
                    notiDTO.setUserId(applicationDTO.getApplicantId());
                    notiDTO.setRoute("/job-history");
                    try {
                        notificationService.sendNotification(notiDTO);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            return x;
        }).toList();
                job.setApplicants(applicants);
                jobRepository.save(job);
    }


}
