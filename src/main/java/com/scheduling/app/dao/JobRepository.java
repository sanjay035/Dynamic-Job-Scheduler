package com.scheduling.app.dao;

import com.scheduling.app.model.JobModel;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<JobModel, Long> {
	JobModel findByJobName(String JobName);

	Integer deleteByJobName(String JobName);
}
