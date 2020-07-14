package com.zetton.thymeleaf.sprintBatch;

import com.zetton.thymeleaf.config.CommonProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchServiceTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommonProperties p;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("scoreJob")
    private Job scoreJob;

    @Autowired
    @Qualifier("commonJob")
    private Job commonJob;

    private static final String KEY_JOB_NAME = "input.job.name";
    private static final String KEY_FILE_NAME = "input.file.name";
    private static final String KEY_VO_NAME = "input.vo.name";
    private static final String KEY_COLUMNS = "input.columns";
    private static final String KEY_SQL = "input.sql";

    @Test
    public void testBatch() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .addString("input.file.name", "F:\\score.csv")
                .toJobParameters();
        jobLauncher.run(scoreJob, jobParameters);
        logger.info("testBatch执行完成");
        Thread.sleep(2000L);
    }

    /**
     * 测试一个配置类，可同时运行多个任务
     * @throws Exception 异常
     */
    @Test
    public void testCommonJobs() throws Exception {
        JobParameters jobParameters1 = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .addString(KEY_JOB_NAME, "App")
                .addString(KEY_FILE_NAME, p.getXlsApp())
                .addString(KEY_VO_NAME, "com.xncoding.trans.modules.zapp.App")
                .addString(KEY_COLUMNS, String.join(",", new String[]{
                        "appid", "zname", "flag"
                }))
                .addString(KEY_SQL, "insert into z_test_App (appid, zname, flag) values(:appid, :zname, :flag)")
                .toJobParameters();
        jobLauncher.run(commonJob, jobParameters1);

        JobParameters jobParameters2 = new JobParametersBuilder()
                .addLong("time",System.currentTimeMillis())
                .addString(KEY_JOB_NAME, "Log")
                .addString(KEY_FILE_NAME, p.getXlsLog())
                .addString(KEY_VO_NAME, "com.xncoding.trans.modules.zlog.Log")
                .addString(KEY_COLUMNS, String.join(",", new String[]{
                        "logid", "msg", "logtime"
                }))
                .addString(KEY_SQL, "insert into z_test_Log (logid, msg, logtime) values(:logid, :msg, :logtime)")
                .toJobParameters();
        jobLauncher.run(commonJob, jobParameters2);

        logger.info("Main线程执行完成");

        while (true) {
            Thread.sleep(2000000L);
        }
    }
}