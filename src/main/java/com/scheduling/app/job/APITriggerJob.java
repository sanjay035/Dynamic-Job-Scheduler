package com.scheduling.app.job;

import com.scheduling.app.constants.JobConstants;
import com.scheduling.app.model.JobModel;
import com.scheduling.app.util.LoggerWrapper;
import com.scheduling.app.util.RestTemplateUtil;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class APITriggerJob implements Job {

    private static final Logger LOG = LoggerWrapper.getLogger(APITriggerJob.class);

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        JobModel jobModel = (JobModel) jobDataMap.get(JobConstants.JOB_DATA);
        String jobID = String.valueOf(jobDataMap.get(JobConstants.JOB_ID));

        LOG.info("The JobID is `" + jobID + "` --- Job name is `" + jobModel.getJobName() + "`");

        System.out.println(jobModel);

        String reqType = jobModel.getRequestType().toUpperCase();

        switch (reqType) {
            case "GET":
                restTemplateUtil.performGetRequest(jobModel);
                break;
            case "POST":
                restTemplateUtil.performPOSTRequest(jobModel);
                break;
            case "PUT":
                restTemplateUtil.performPUTRequest(jobModel);
                break;
            case "DELETE":
                restTemplateUtil.performDeleteRequest(jobModel);
                break;
            default:
                throw new JobExecutionException();
        }
    }
}
