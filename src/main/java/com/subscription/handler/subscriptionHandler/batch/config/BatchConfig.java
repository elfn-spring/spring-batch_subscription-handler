package com.subscription.handler.subscriptionHandler.batch.config;

import com.subscription.handler.subscriptionHandler.batch.mappers.SubscriptionRowMapper;
import com.subscription.handler.subscriptionHandler.batch.processors.SubscriptionProcessor;
import com.subscription.handler.subscriptionHandler.batch.writer.SubscriptionWriter;
import com.subscription.handler.subscriptionHandler.entities.Subscription;
import com.subscription.handler.subscriptionHandler.services.MailSender;
import org.quartz.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.sql.DataSource;
import java.io.File;

/**
 * @PROJECT subscriptionHandler
 * @Author Elimane on 20/05/2022
 */
@SuppressWarnings("SpringConfigurationProxyMethods")
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig extends QuartzJobBean {

  @Autowired
  MailSender mailSender;

  @Autowired
  public JobBuilderFactory jobBuilderFactory;
  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Autowired
  public DataSource datasource;

  @Autowired
  public JobExplorer jobExplorer;

  @Autowired
  public JobLauncher jobLauncher;

  //To determine when we execute our job
  @Bean
  public Trigger trigger(){
    SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
      .simpleSchedule()
      .withIntervalInSeconds(20)//To launch our job every thirty seconds
      .repeatForever();

    return TriggerBuilder.newTrigger()
      .forJob(jobDetail())
      .withSchedule(scheduleBuilder)
      .build();
  }

  @Bean
  public JobDetail jobDetail(){
    return JobBuilder.newJob(BatchConfig.class)
      .storeDurably()
      .build();
  }

  @Override
  protected void executeInternal(org.quartz.JobExecutionContext context) {
    try {
      JobParameters params =  new JobParametersBuilder(jobExplorer)
        .getNextJobParameters(job(mailSender))//To auto increment job parameters
        .toJobParameters();

      this.jobLauncher.run(job(mailSender),params);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Bean
  public PagingQueryProvider queryProvider() throws Exception {

    SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
    factory.setSelectClause("select id, status, subscription_type, expiration_date,subscription_date, name");
    factory.setFromClause( "from subscription");
    factory.setSortKey("id");
    factory.setDataSource(datasource);

    return factory.getObject();

  }

  @Bean
  @StepScope
  public ItemReader<Subscription> itemReader() throws Exception {

    JdbcPagingItemReader<Subscription> reader = new JdbcPagingItemReader<>();

    reader.setDataSource(datasource);
    reader.setFetchSize(5);
    reader.setPageSize(5);
    reader.setQueryProvider(queryProvider());
    reader.setRowMapper(new SubscriptionRowMapper());
    reader.afterPropertiesSet();
    return reader;

//        return new JdbcPagingItemReaderBuilder<Product>()
//                .dataSource(datasource)
//                .name("jdbccursoritemreader")
//                .queryProvider(queryProvider())
//                .rowMapper(new ProductRowMapper())
//                .pageSize(10)
//                .build();
  }

  @Bean
  public ItemProcessor<Subscription,Subscription> subscriptionProcessor() {
    return new SubscriptionProcessor();
  }

  @Bean
  public ItemWriter<? super Subscription> subscriptionWriter(MailSender mailSender) {
    return new SubscriptionWriter(mailSender);
  }




  @Bean
  public Step chunkBasedStep(MailSender mailSender) throws Exception {
    return this.stepBuilderFactory.get("chunkbasedstep")
      .<Subscription, Subscription>chunk(10)
      .reader(itemReader())
      .processor(subscriptionProcessor())
      .writer(subscriptionWriter(mailSender))
      .build();
  }



  @Bean
  public Job job(MailSender mailSender) throws Exception {
    return  this.jobBuilderFactory.get("job2J")
      .incrementer(new RunIdIncrementer())//To increment jobparameters for scheduling with quartz
      .start(chunkBasedStep(mailSender))
      .build();
  }


}
