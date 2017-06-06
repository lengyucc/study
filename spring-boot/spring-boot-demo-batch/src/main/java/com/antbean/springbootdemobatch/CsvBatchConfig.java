package com.antbean.springbootdemobatch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing // 开启批处理支持
public class CsvBatchConfig {
	@Bean
	public ItemReader<Person> reader() throws Exception {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<Person>(); // 使用FlatFileItemReader读取文件
		reader.setResource(new ClassPathResource("people.csv")); // 使用FlatFileItemReader的setResource方法设置csv文件路径
		reader.setLineMapper(new DefaultLineMapper<Person>() { // 对csv文件数据和领域模型类做对应映射
			{
				setLineTokenizer(new DelimitedLineTokenizer() {
					{
						setNames(new String[] { "name", "age", "nation", "address" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
					{
						setTargetType(Person.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		CsvItemProcessor processor = new CsvItemProcessor(); // 使用自己定义的ItemProcessor的实现CsvItemProcessor
		processor.setValidator(csvBeanValidator()); // 为processor设置校验器为CsvBeanValidator
		return processor;
	}

	@Bean
	public ItemWriter<Person> writer(DataSource dataSource) {	// Spring能让容器中已有的bean以参数形式注入,spring boot已为我们定义了DataSource
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();	// 使用jdbc批处理JdbcBatchItemWriter来讲数据写到数据库
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		String sql = "insert into PERSON (name, age, nation, address) values (:name, :age, :nation, :address)";
		writer.setSql(sql);	// 设置要执行批处理的sql语句
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
			throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource);
		jobRepositoryFactoryBean.setTransactionManager(transactionManager);
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean
	public SimpleJobLauncher jobLauncher(DataSource dataSource, PlatformTransactionManager transactionManager,
			JobRepository jobRepository) {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository);
		return jobLauncher;
	}

	@Bean
	public Job importJob(JobBuilderFactory jobs, Step step, JobExecutionListener jobExecutionListener) {
		return jobs.get("importJob") //
				.incrementer(new RunIdIncrementer()) //
				.flow(step) //
				.end() //
				.listener(jobExecutionListener) //
				.build();
	}

	@Bean
	public Step step(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
			ItemProcessor<Person, Person> processor) {
		return stepBuilderFactory //
				.get("step") //
				.<Person, Person> chunk(65000) // 批处理每次提交65000条数据
				.reader(reader) //
				.processor(processor) //
				.writer(writer) //
				.build();

	}

	@Bean
	public CsvJobListener csvJobListener() {
		return new CsvJobListener();
	}

	@Bean
	public Validator<Person> csvBeanValidator() {
		return new CsvBeanValidator<Person>();
	}

}
