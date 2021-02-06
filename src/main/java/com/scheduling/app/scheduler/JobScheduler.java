package com.scheduling.app.scheduler;

import com.scheduling.app.constants.ErrorCodes;
import com.scheduling.app.constants.JobConstants;
import com.scheduling.app.exception.FinalException;
import com.scheduling.app.model.JobModel;
import com.scheduling.app.util.JobUtil;
import com.scheduling.app.util.LoggerWrapper;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Service
public class JobScheduler {

	private static final Logger LOG = LoggerWrapper.getLogger(JobScheduler.class);

	private final Scheduler scheduler;

	private static long maxJobID;

	@Autowired
	public JobScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void insertJob(final Class jobClass, final JobModel jobModel) throws FinalException {
		try {
			final JobDetail jobDtl = this.scheduler.getJobDetail(new JobKey(String.valueOf(jobModel.getJobID())));

			if (jobDtl != null)
				throw new FinalException(ErrorCodes.CLIENT_ERROR, JobConstants.JOB_EXISTS);

			final JobDetail jobDetail = JobUtil.buildJobDetail(jobClass, jobModel, jobModel.getJobID());
			final Trigger trigger = JobUtil.buildTrigger(jobClass, jobModel, jobModel.getJobID());

			this.scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException se) {
			LOG.error(se.getMessage(), se);
		}
	}

	public JobModel updateJob(final Class jobClass, final JobModel jobModel) throws FinalException {
		try {
			final JobModel oldJobModel = this.getJobDetailsByID(jobModel.getJobID());

			// Updating the existing job with new trigger
			if (!oldJobModel.getCronExpression().equals(jobModel.getCronExpression())) {
				final Trigger newTrigger = JobUtil.buildTrigger(jobClass, jobModel, jobModel.getJobID());
				this.scheduler.rescheduleJob(new TriggerKey(String.valueOf(jobModel.getJobID())), newTrigger);
			}

			// Updating the job data if any
			final JobDetail jobDetail = this.scheduler.getJobDetail(new JobKey(String.valueOf(jobModel.getJobID())));

			jobDetail.getJobDataMap().put(JobConstants.JOB_DATA, jobModel);
			this.scheduler.addJob(jobDetail, true, true);

			return (JobModel) jobDetail.getJobDataMap().get(JobConstants.JOB_DATA);
		} catch (SchedulerException se) {
			LOG.error(se.getMessage(), se);
			return null;
		}
	}

	public JobModel getJobDetailsByID(final long jobID) throws FinalException {
		try {
			final JobDetail jobDetail = this.scheduler.getJobDetail(new JobKey(String.valueOf(jobID)));

			if (jobDetail == null)
				throw new FinalException(ErrorCodes.CLIENT_ERROR, JobConstants.JOB_NOT_EXISTS);

			return (JobModel) jobDetail.getJobDataMap().get(JobConstants.JOB_DATA);
		} catch (SchedulerException se) {
			LOG.error(se.getMessage(), se);
			return null;
		}
	}

	public JobModel deleteJob(final long jobID) throws FinalException {
		try {
			final JobModel jobModel = this.getJobDetailsByID(jobID);

			this.scheduler.deleteJob(new JobKey(String.valueOf(jobID)));

			return jobModel;
		} catch (SchedulerException se) {

			LOG.error(se.getMessage(), se);
			return null;
		}
	}

	public boolean activateJob(final long jobID) throws FinalException {
		try {
			final JobModel jobModel = this.getJobDetailsByID(jobID);

			this.scheduler.resumeTrigger(new TriggerKey(String.valueOf(jobID)));

			return true;
		} catch (SchedulerException se) {

			LOG.error(se.getMessage(), se);
			return false;
		}
	}

	public boolean deactivateJob(final long jobID) throws FinalException {
		try {
			final JobModel jobModel = this.getJobDetailsByID(jobID);

			this.scheduler.pauseTrigger(new TriggerKey(String.valueOf(jobID)));

			return true;
		} catch (SchedulerException se) {

			LOG.error(se.getMessage(), se);
			return false;
		}
	}

	@PostConstruct
	public void init() {
		try {
			this.scheduler.start();

			maxJobID = this.scheduler.getJobKeys(GroupMatcher.anyGroup()).stream().map(jobKey -> {
				try {
					return this.scheduler.getJobDetail(jobKey).getJobDataMap();
				} catch (SchedulerException se) {
					LOG.error(se.getMessage(), se);
					return null;
				}
			}).filter(Objects::nonNull).mapToLong(x -> (long) x.get(JobConstants.JOB_ID)).max().orElse(0);

		} catch (SchedulerException se) {
			LOG.error(se.getMessage(), se);
		}
	}

	@PreDestroy
	public void destroy() {
		try {
			this.scheduler.shutdown();
		} catch (SchedulerException se) {
			LOG.error(se.getMessage(), se);
		}
	}
}
