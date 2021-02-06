package com.scheduling.app.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Entity(name = "CronJobs")
public class JobModel implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobID;
	private String jobName;
	private String description;
	private String cronExpression;
	private String requestUrl;
	private String requestType;
	private String callbackUrl;
	private int retryCount;
	private int delayBetweenRetries;
	private boolean isActive;
	@Column(columnDefinition = "JSON")
	private String param;
	@Column(columnDefinition = "JSON")
	private String headers;
	@Column(columnDefinition = "JSON")
	private String body;

	public long getJobID() {
		return jobID;
	}

	public void setJobID(long jobID) {
		this.jobID = jobID;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Integer getDelayBetweenRetries() {
		return delayBetweenRetries;
	}

	public void setDelayBetweenRetries(Integer delayBetweenRetries) {
		this.delayBetweenRetries = delayBetweenRetries;
	}

	public boolean IsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public JsonNode getParam() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(param);
		return actualObj;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public JsonNode getHeaders() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(headers);
		return actualObj;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public JsonNode getBody() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readTree(body);
		return actualObj;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	@Override
	public String toString() {
		return "JobModel{" + "jobID=" + jobID + ", jobName='" + jobName + '\'' + ", description='" + description + '\'' + ", cronExpression='"
				+ cronExpression + '\'' + ", requestUrl='" + requestUrl + '\'' + ", requestType='" + requestType + '\'' + ", callbackUrl='"
				+ callbackUrl + '\'' + ", retryCount=" + retryCount + ", delayBetweenRetries=" + delayBetweenRetries + ", isActive=" + isActive
				+ ", param='" + param + '\'' + ", headers='" + headers + '\'' + ", body='" + body + '\'' + '}';
	}
}
