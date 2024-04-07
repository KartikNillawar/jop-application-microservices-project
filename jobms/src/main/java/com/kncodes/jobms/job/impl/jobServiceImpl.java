package com.kncodes.jobms.job.impl;


import com.kncodes.jobms.job.Job;
import com.kncodes.jobms.job.JobRepository;
import com.kncodes.jobms.job.clients.CompanyClient;
import com.kncodes.jobms.job.clients.ReviewClient;
import com.kncodes.jobms.job.dto.JobDTO;
import com.kncodes.jobms.job.external.Company;
import com.kncodes.jobms.job.external.Review;
import com.kncodes.jobms.job.jobService;
import com.kncodes.jobms.job.mapper.JobMapper;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class jobServiceImpl implements jobService {
    //    private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired
    RestTemplate restTemplate;

    private CompanyClient companyClient;
    private ReviewClient reviewClient;
    public jobServiceImpl(JobRepository jobRepository,CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();


        RestTemplate restTemplate = new RestTemplate();


        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private JobDTO convertToDto(Job job)
    {
        Company company = companyClient.getCompany(job.getCompanyId());

        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job,company, reviews);
        return jobDTO;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id) {

        Job job =  jobRepository.findById(id).orElse(null);
        return convertToDto(job);
    }

    @Override
    public boolean deleteJob(Long jobId) {
        try {
            jobRepository.deleteById(jobId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long jobId, Job updatedjob) {
        Optional<Job> jobOptional = jobRepository.findById(jobId);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedjob.getTitle());
            job.setDescription(updatedjob.getDescription());
            job.setLocation(updatedjob.getLocation());
            job.setMinSalary(updatedjob.getMinSalary());
            job.setMaxSalary(job.getMaxSalary());
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
