package com.scheduling.app.controller;

import com.scheduling.app.exception.FinalException;
import com.scheduling.app.request.JobRequest;
import com.scheduling.app.service.APISchedulerService;
import com.scheduling.app.util.ResponseGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scheduler")
public class APISchedulerController {

	@Autowired
	private APISchedulerService apiSchedulerService;

	@PostMapping(path = "/insert-job")
	public ResponseEntity<Object> insertJob(@RequestBody JobRequest jobRequest) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.insertJob(jobRequest)), HttpStatus.OK);
	}

	@PutMapping(path = "/update-job")
	public ResponseEntity<Object> updateJob(@RequestBody JobRequest jobRequest) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.updateJob(jobRequest)), HttpStatus.OK);
	}

	@GetMapping(path = "/get-job/{jobID}")
	public ResponseEntity<Object> getJobDetailsByID(@PathVariable("jobID") long jobID) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.getJobDetailsByID(jobID)), HttpStatus.OK);
	}

	@DeleteMapping(path = "/delete-job/{jobID}")
	public ResponseEntity<Object> deleteJob(@PathVariable("jobID") long jobID) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.deleteJob(jobID)), HttpStatus.OK);
	}

	@PutMapping(path = "/activate-job/{jobID}")
	public ResponseEntity<Object> activateJob(@PathVariable("jobID") long jobID) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.activateJob(jobID)), HttpStatus.OK);
	}

	@PutMapping(path = "/deactivate-job/{jobID}")
	public ResponseEntity<Object> deactivateJob(@PathVariable("jobID") long jobID) throws FinalException {
		return new ResponseEntity<>(ResponseGeneratorUtil.okResponse(apiSchedulerService.deactivateJob(jobID)), HttpStatus.OK);
	}
}
