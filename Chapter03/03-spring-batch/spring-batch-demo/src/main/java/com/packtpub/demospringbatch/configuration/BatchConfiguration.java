package com.packtpub.demospringbatch.configuration;

import com.packtpub.demospringbatch.configuration.javachampionsloader.JavaChampionsReader;
import com.packtpub.demospringbatch.domain.JavaChampion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    public static final String STEP_PROCESS_CSV_FILE = "LoadJavaChampionsFromFile";

    public static final String DATA_READER = "ReadInformationFromCsvFile";
    public static final String DATA_PROCESSOR = "ProcessInformation";
    public static final String DATA_WRITER = "WriteProcessedInformation";

    public static final String JAVA_CHAMPIONS_LOADER_JOB = "javaChampionsLoaderJob";

    @Bean
    public Job javaChampionsLoader(JobBuilderFactory jobs, @Qualifier(STEP_PROCESS_CSV_FILE) Step step) {
        return jobs.get(JAVA_CHAMPIONS_LOADER_JOB)
                .flow(step)
                .end()
                .build();
    }


    @Bean(name = STEP_PROCESS_CSV_FILE)
    public Step readCsvFileAndPopulateDbTable(
            StepBuilderFactory stepBuilderFactory,
            PlatformTransactionManager platformTransactionManager,
            @Qualifier(DATA_READER) ItemReader<JavaChampion> itemReader,
            @Qualifier(DATA_PROCESSOR) ItemProcessor<JavaChampion, JavaChampion> itemProcessor,
            @Qualifier(DATA_WRITER) ItemWriter<JavaChampion> itemWriter) {

        StepBuilder builder = stepBuilderFactory.get(STEP_PROCESS_CSV_FILE);

        return builder.<JavaChampion, JavaChampion>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }


    @Bean(name = DATA_READER)
    @StepScope
    public FlatFileItemReader<JavaChampion> reader() throws Exception {
        String fileName = "java-champions.csv";
        return new JavaChampionsReader(fileName);
    }


    @Bean(name = DATA_PROCESSOR)
    public ItemProcessor<JavaChampion, JavaChampion> processor() {
        return objectFromRow -> {
            log.info("Java Champion found: " + objectFromRow);
            return objectFromRow;
        };
    }


    @Bean(name = DATA_WRITER)
    public JdbcBatchItemWriter<JavaChampion> writer(DataSource dataSource) throws Exception {
        JdbcBatchItemWriter<JavaChampion> jdbcBatchItemWriter = new JdbcBatchItemWriter<JavaChampion>();
        jdbcBatchItemWriter.setAssertUpdates(true);
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql(
                " INSERT INTO java_champion( first_name, last_name, country, year )" +
                        " VALUES ( :firstName , :lastName, :country, :year ) ");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return jdbcBatchItemWriter;
    }


    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
