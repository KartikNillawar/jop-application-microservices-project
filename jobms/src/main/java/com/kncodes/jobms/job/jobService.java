package com.kncodes.jobms.job;

import com.kncodes.jobms.job.dto.JobDTO;

import java.util.List;

public interface jobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJob(Long jobId);

    boolean updateJob(Long jobId, Job job);
}
