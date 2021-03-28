package com.example.demo;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import lombok.extern.java.Log;

@Log
public class JobResponseHandler extends JobExecutionListenerSupport {


    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job being end");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
    	log.info("Job being start");
    }

}
