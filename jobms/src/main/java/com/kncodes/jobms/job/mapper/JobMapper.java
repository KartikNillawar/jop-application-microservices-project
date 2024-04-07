package com.kncodes.jobms.job.mapper;

import com.kncodes.jobms.job.Job;
import com.kncodes.jobms.job.dto.JobDTO;
import com.kncodes.jobms.job.external.Company;
import com.kncodes.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobWithCompanyDto(
            Job job,
            Company company,
            List<Review> reviews
    ){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setCompany(company);
        jobDTO.setId(job.getId());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMaxSalary(job.getMinSalary());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}
