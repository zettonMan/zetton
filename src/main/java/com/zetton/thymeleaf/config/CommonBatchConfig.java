package com.zetton.thymeleaf.config;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.support.DatabaseType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class CommonBatchConfig {
    @Bean
    public ThreadPoolTaskExecutor taskBatchExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(200);
        return taskExecutor;
    }

    /**
     * JobRepository，用来注册Job的容器
     * jobRepositor的定义需要dataSource和transactionManager，Spring Boot已为我们自动配置了
     * 这两个类，Spring可通过方法注入已有的Bean
     *
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public JobRepository batchJobRepository(DataSource dataSource, PlatformTransactionManager transactionManager) throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        jobRepositoryFactoryBean.setDatabaseType(String.valueOf(DatabaseType.MYSQL));
        jobRepositoryFactoryBean.setMaxVarCharLength(5000);
        // 下面事务隔离级别的配置是针对Oracle的
        /*jobRepositoryFactoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        jobRepositoryFactoryBean.afterPropertiesSet();*/
        return jobRepositoryFactoryBean.getObject();
    }

    /**
     * JobLauncher定义，用来启动Job的接口
     *
     * @param dataSource
     * @param transactionManager
     * @return
     * @throws Exception
     */
    @Bean
    public SimpleJobLauncher batchJobLauncher(ThreadPoolTaskExecutor taskBatchExecutor, DataSource dataSource,
                                         PlatformTransactionManager transactionManager) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setTaskExecutor(taskBatchExecutor);
        jobLauncher.setJobRepository( batchJobRepository(dataSource, transactionManager));
        return jobLauncher;
    }
}
