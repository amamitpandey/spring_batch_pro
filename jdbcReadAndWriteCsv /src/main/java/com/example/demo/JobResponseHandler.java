package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class JobResponseHandler extends JobExecutionListenerSupport {

    Logger logger = LoggerFactory.getLogger(JobResponseHandler.class);

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("Job being end");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! JOB FINISHED! Time to verify the results");
        }
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Job being start");
    }

}
