package com.scheduling.app.service;

import com.scheduling.app.constants.ErrorCodes;
import com.scheduling.app.constants.JobConstants;
import com.scheduling.app.dao.JobRepository;
import com.scheduling.app.exception.FinalException;
import com.scheduling.app.job.APITriggerJob;
import com.scheduling.app.model.JobModel;
import com.scheduling.app.request.JobRequest;
import com.scheduling.app.response.JobResponse;
import com.scheduling.app.scheduler.JobScheduler;
import com.scheduling.app.util.JobUtil;
import com.scheduling.app.util.LoggerWrapper;
import com.scheduling.app.util.RequestValidatorUtil;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SchedulerService {

    private static final Logger LOG = LoggerWrapper.getLogger(SchedulerService.class);

    private final JobRepository jobRepository;

    private final JobScheduler jobScheduler;

    private final RequestValidatorUtil requestValidatorUtil;

    @Autowired
    public SchedulerService(JobScheduler jobScheduler, JobRepository jobRepository, RequestValidatorUtil requestValidatorUtil) {
        this.jobScheduler = jobScheduler;
        this.jobRepository = jobRepository;
        this.requestValidatorUtil = requestValidatorUtil;
    }

    public JobResponse insertJob(JobRequest jobRequest) throws FinalException {
        try {
            this.requestValidatorUtil.validateJobRequest(jobRequest);

            JobModel jobModel = JobUtil.getJobModel(jobRequest);
            jobModel = this.jobRepository.save(jobModel);
            jobRequest.setJobID(jobModel.getJobID());

            this.jobScheduler.insertJob(APITriggerJob.class, jobModel);

            return JobUtil.getJobResponse(jobModel);
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }

    public JobResponse updateJob(JobRequest jobRequest) throws FinalException {
        try {
            this.requestValidatorUtil.validateJobRequest(jobRequest);

            JobModel jobModel = JobUtil.getJobModel(jobRequest);
            this.jobScheduler.updateJob(APITriggerJob.class, jobModel);

            jobModel = this.jobRepository.save(jobModel);

            return JobUtil.getJobResponse(jobModel);
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }

    public JobModel getJobDetailsByID(long jobID) throws FinalException {
        try {
            return this.jobScheduler.getJobDetailsByID(jobID);
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }

    @Transactional
    public JobModel deleteJob(long jobID) throws FinalException {
        try {
            JobModel jobModel = this.jobScheduler.deleteJob(jobID);
            this.jobRepository.deleteById(jobID);

            return jobModel;
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }

    public String activateJob(long jobID) throws FinalException {
        try {
            boolean isActivated = this.jobScheduler.activateJob(jobID);

            Optional<JobModel> jobModel = this.jobRepository.findById(jobID);
            jobModel.get().setActive(isActivated);
            this.jobRepository.save(jobModel.get());

            return isActivated ? JobConstants.JOB_ACTIVATED : JobConstants.JOB_NOT_ACTIVATED;
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }

    public String deactivateJob(long jobID) throws FinalException {
        try {
            boolean isDeactivated = this.jobScheduler.deactivateJob(jobID);

            Optional<JobModel> jobModel = this.jobRepository.findById(jobID);
            jobModel.get().setActive(!isDeactivated);
            this.jobRepository.save(jobModel.get());

            return isDeactivated ? JobConstants.JOB_DEACTIVATED : JobConstants.JOB_NOT_DEACTIVATED;
        } catch (DataIntegrityViolationException de) {
            throw new FinalException(ErrorCodes.CLIENT_ERROR, de.getMessage());
        }
    }
}
