package com.scheduling.app.request;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.lang.Nullable;

import java.util.*;

public class JobRequest {
	@Nullable
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
	private JsonNode param;
	private JsonNode headers;
	private JsonNode body;

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

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public JsonNode getParam() {
		return param;
	}

	public void setParam(JsonNode param) {
		this.param = param;
	}

	public JsonNode getHeaders() {
		return headers;
	}

	public void setHeaders(JsonNode headers) {
		this.headers = headers;
	}

	public JsonNode getBody() {
		return body;
	}

	public void setBody(JsonNode body) {
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

	public Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	@Override
	public String toString() {
		return "JobRequest{" + "jobID=" + jobID + ", jobName='" + jobName + '\'' + ", description='" + description + '\'' + ", cronExpression='"
				+ cronExpression + '\'' + ", requestUrl='" + requestUrl + '\'' + ", requestType='" + requestType + '\'' + ", callbackUrl='"
				+ callbackUrl + '\'' + ", retryCount=" + retryCount + ", delayBetweenRetries=" + delayBetweenRetries + ", isActive=" + isActive
				+ ", param=" + param + ", headers=" + headers + ", body=" + body + '}';
	}
}
