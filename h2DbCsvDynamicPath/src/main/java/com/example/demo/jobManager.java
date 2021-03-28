package com.example.demo;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
@Configuration
@EnableBatchProcessing
public class jobManager {
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	DataSource dataSource;

	@Bean
	Job jobMangerOne() {
		return jobBuilderFactory.get("jobMangerOne")
				.incrementer(new RunIdIncrementer())
				.listener(new JobResponseHandler())
				.flow(step1())
				.end().build();
	}

	@Bean
	Step step1() {
		return stepBuilderFactory.get("step1")
				.<StudenBean, StudenBean>chunk(1).reader(reader())
				// .processor(null)
				.writer(writer()).build();
	}
	
    @Bean
    @StepScope
    Resource inputFileResource(@Value("#{jobParameters[CustomfileName]}") final String CustomfileName) {
        return new ClassPathResource(CustomfileName);
    }

	@Bean
	FlatFileItemReader<StudenBean> reader() {
		return new FlatFileItemReaderBuilder<StudenBean>().name("reader")
                .resource(inputFileResource(null))
				.delimited()
				.names(new String[] { "stu_id", "stu_name", "stu_sub", "stu_no" })
				.fieldSetMapper(new BeanWrapperFieldSetMapper<StudenBean>() {
					{
						setTargetType(StudenBean.class);
					}
				}).build();
	}

	@Bean
	public JdbcBatchItemWriter<StudenBean> writer() {
		return new JdbcBatchItemWriterBuilder<StudenBean>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO stu_table (stu_id, stu_name, stu_sub, stu_no) VALUES (:stu_id, :stu_name, :stu_sub, :stu_no)")
				.dataSource(dataSource).build();
	}

}
