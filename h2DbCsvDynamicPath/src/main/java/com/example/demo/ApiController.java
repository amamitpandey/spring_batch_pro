package com.example.demo;

import java.util.Date;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
	
	// @Autowired 
	// Autowired follow IOC of spring, if we don't use @component, show error
	// alternative we can use default instant
	StudenBean stuBean = new StudenBean(); 
	
	@Autowired
	StudentRepo repo;
	
	@GetMapping("/save-stu")
	StudenBean saveStu(@PathVariable Integer id){
		StudenBean studenBean = repo.findById(id).get();
		return studenBean;
	}
	
	@GetMapping("/getStudent/{id}")
	StudenBean getStudent(@PathVariable Integer id){
		StudenBean studenBean = repo.findById(id).get();
		return studenBean;
	}
	
	@Autowired
	Job job;
	
	@Autowired
	JobLauncher jobLauncher;
	
	
	@GetMapping("/run-batch")
	String runBatch() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
		jobParametersBuilder.addDate("time", new Date(),true);
		jobParametersBuilder.addString("CustomfileName", "student-sheet.csv");
		JobExecution jobExecution = jobLauncher.run(job, jobParametersBuilder.toJobParameters());
		return "job Completed";
	}

}
