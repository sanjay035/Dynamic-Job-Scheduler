package com.scheduling.app.util;

import com.scheduling.app.constants.JobConstants;
import com.scheduling.app.exception.FinalException;
import com.scheduling.app.model.JobModel;
import com.scheduling.app.request.JobRequest;
import com.scheduling.app.response.JobResponse;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public final class JobUtil {
	public static JobDetail buildJobDetail(final Class jobClass, final JobModel jobModel, final long jobID) throws FinalException {
		final JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(JobConstants.JOB_DATA, jobModel);
		jobDataMap.put(JobConstants.JOB_ID, jobID);
		return JobBuilder.newJob(jobClass).withIdentity(String.valueOf(jobID)).setJobData(jobDataMap).build();
	}

	public static Trigger buildTrigger(final Class jobClass, final JobModel jobModel, final long jobID) throws FinalException {
		CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule(jobModel.getCronExpression());
		return TriggerBuilder.newTrigger().withIdentity(String.valueOf(jobID)).withSchedule(builder).startNow().build();
	}

	public static JobModel getJobModel(JobRequest jobRequest) {
		JobModel jobModel = new JobModel();
		jobModel.setJobID(jobRequest.getJobID());
		jobModel.setJobName(jobRequest.getJobName());
		jobModel.setCronExpression(jobRequest.getCronExpression());
		jobModel.setDescription(jobRequest.getDescription());
		jobModel.setRequestUrl(jobRequest.getRequestUrl());
		jobModel.setRequestType(jobRequest.getRequestType());
		jobModel.setCallbackUrl(jobRequest.getCallbackUrl());
		jobModel.setRetryCount(jobRequest.getRetryCount());
		jobModel.setDelayBetweenRetries(jobRequest.getDelayBetweenRetries());
		jobModel.setIsActive(jobRequest.getIsActive());
		jobModel.setParam(jobRequest.getParam().toString());
		jobModel.setHeaders(jobRequest.getHeaders().toString());
		jobModel.setBody(jobRequest.getBody().toString());
		return jobModel;
	}

	public static JobResponse getJobResponse(JobModel jobModel) {
		JobResponse jobResponse = new JobResponse();
		jobResponse.setJobID(jobModel.getJobID());
		jobResponse.setJobName(jobModel.getJobName());
		jobResponse.setActive(jobModel.IsActive());
		return jobResponse;
	}
}
