package com.zetton.thymeleaf.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CommonJobListener implements JobExecutionListener {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private long startTime;
    private long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        String jobName = jobExecution.getJobParameters().getString("input.file.name");
        String columns = jobExecution.getJobParameters().getString("input.columns");
        String name = jobExecution.getJobParameters().getString("input.vo.name");
        String sql = jobExecution.getJobParameters().getString("input.sql");

        logger.info("任务-{}处理开始", jobName, columns, name, sql);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        String jobName = jobExecution.getJobParameters().getString("input.file.name");
        logger.info("任务-{}处理结束，总耗时=" + (endTime - startTime) + "ms", jobName);
    }
}
