package com.zetton.thymeleaf.config;

import com.zetton.thymeleaf.entity.Score;
import com.zetton.thymeleaf.listener.ScoreJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class ExcelBatchConfig {

    /**
     * ItemReader定义,用来读取数据
     * 1，使用FlatFileItemReader读取文件
     * 2，使用FlatFileItemReader的setResource方法设置csv文件的路径
     * 3，对此对excel文件的数据和模型类做对应映射
     *
     * @return FlatFileItemReader
     */
    @Bean(name = "scoreReader")
    @StepScope
    public FlatFileItemReader<Score> reader(@Value("#{jobParameters['input.file.name']}") String pathToFile) {
        FlatFileItemReader<Score> reader = new FlatFileItemReader<>();
        // reader.setResource(new ClassPathResource(pathToFile));
        reader.setResource(new FileSystemResource(pathToFile));
        reader.setLineMapper(new DefaultLineMapper<Score>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer(",") {
                    {
                        setNames(new String[]{
                                "id","studentId","score","subject"
                        });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Score>() {
                    {
                        setTargetType(Score.class);
                    }
                });
            }
        });
        return reader;
    }

    /**
     * ItemProcessor定义，用来处理数据
     *
     * @return
     */
    @Bean(name = "scoreProcessor")
    public ItemProcessor<Score, Score> processor() {
        //使用我们自定义的ItemProcessor的实现CsvItemProcessor
        ValidatingItemProcessor<Score> processor = new ValidatingItemProcessor<Score>() {
            @Override
            public Score process(Score item) throws ValidationException {
                /*
                 * 需要执行super.process(item)才会调用自定义校验器
                 */
                super.process(item);
                /*
                 * 对数据进行简单的处理和转换 todo
                 */
                return item;
            }
        };
        //为processor指定校验器为CsvBeanValidator()
        processor.setValidator(xlsBeanValidator());
        return processor;
    }

    /**
     * ItemWriter定义，用来输出数据
     * spring能让容器中已有的Bean以参数的形式注入，Spring Boot已经为我们定义了dataSource
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "scoreWriter")
    public ItemWriter<Score> writer(DynamicDataSource dataSource) {
        JdbcBatchItemWriter<Score> writer = new JdbcBatchItemWriter<>();
        //我们使用JDBC批处理的JdbcBatchItemWriter来写数据到数据库
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        String sql = "insert into t_score " + " (id,studentId,score,subject) "
                + " values(:id,:studentId,:score,:subject)";
        //在此设置要执行批处理的SQL语句
        writer.setSql(sql);
        writer.setDataSource(dataSource);
        return writer;
    }

    /**
     * Job定义，我们要实际执行的任务，包含一个或多个Step
     *
     * @param jobBuilderFactory
     * @param s1
     * @return
     */
    @Bean(name = "scoreJob")
    public Job scoreJob(JobBuilderFactory jobBuilderFactory, @Qualifier("scoreStep1") Step s1) {
        return jobBuilderFactory.get("scoreJob")
                .incrementer(new RunIdIncrementer())
                .flow(s1)//为Job指定Step
                .end()
                .listener(new ScoreJobListener())//绑定监听器scoreJobListener
                .build();
    }

    /**
     * step步骤，包含ItemReader，ItemProcessor和ItemWriter
     *
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean(name = "scoreStep1")
    public Step scoreStep1(StepBuilderFactory stepBuilderFactory,
                           @Qualifier("scoreReader") ItemReader<Score> reader,
                           @Qualifier("scoreWriter") ItemWriter<Score> writer,
                           @Qualifier("scoreProcessor") ItemProcessor<Score, Score> processor) {
        return stepBuilderFactory
                .get("scoreStep1")
                .<Score, Score>chunk(5000)//批处理每次提交5000条数据
                .reader(reader)//给step绑定reader
                .processor(processor)//给step绑定processor
                .writer(writer)//给step绑定writer
                .faultTolerant()
                .retry(Exception.class)   // 重试
                .noRetry(ParseException.class)
                .retryLimit(1)           //每条记录重试一次
                .skip(Exception.class)
                .skipLimit(200)         //一共允许跳过200次异常
//                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置每个Job通过并发方式执行，一般来讲一个Job就让它串行完成的好
//                .throttleLimit(10)        //并发任务数为 10,默认为4
                .build();
    }

    @Bean
    public Validator<Score> xlsBeanValidator() {
        return new BatchBeanValidator<>();
    }
}