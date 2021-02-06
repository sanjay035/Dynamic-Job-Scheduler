package com.scheduling.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scheduling.app.model.JobModel;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestTemplateUtil {

    private final static Logger LOG = LoggerWrapper.getLogger(RestTemplateUtil.class);

    @Autowired
    private RestTemplate restTemplate;

    public void performPOSTRequest(JobModel jobModel) {
        try {
            HttpEntity httpEntity = this.prepareHttpEntity(jobModel);
            ResponseEntity<String> responseEntity = restTemplate.exchange(jobModel.getRequestUrl(), HttpMethod.POST, httpEntity, String.class);
            System.out.println(responseEntity);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    public void performPUTRequest(JobModel jobModel) {
        try {
            HttpEntity httpEntity = this.prepareHttpEntity(jobModel);
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(jobModel.getRequestUrl(), HttpMethod.PUT, httpEntity, String.class);
            System.out.println(responseEntity);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    public void performGetRequest(JobModel jobModel) {
        try {
            HttpEntity httpEntity = this.prepareHttpEntity(jobModel);
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(jobModel.getRequestUrl(), HttpMethod.GET, httpEntity, String.class);
            System.out.println(responseEntity);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    public void performDeleteRequest(JobModel jobModel) {
        try {
            HttpEntity httpEntity = this.prepareHttpEntity(jobModel);
            ResponseEntity<String> responseEntity = this.restTemplate.exchange(jobModel.getRequestUrl(), HttpMethod.DELETE, httpEntity, String.class);
            System.out.println(responseEntity);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
        }
    }

    public HttpEntity prepareHttpEntity(JobModel jobModel) {
        try {
            HttpHeaders headers = new HttpHeaders();
            ObjectMapper mapper = new ObjectMapper();

            JsonNode jsonNode = mapper.readTree(jobModel.getHeaders().toString());

            Map<String, String> result = mapper.convertValue(jsonNode, new TypeReference<Map<String, String>>() {
            });

            for (String key : result.keySet()) {
                headers.add(key, result.get(key));
            }

            HttpEntity httpEntity = new HttpEntity(jobModel.getBody(), headers);

            return httpEntity;
        } catch (JsonProcessingException ex) {
            LOG.error(ex.getMessage());
        }
        return null;
    }
}
