package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

    private final HelloWorldTasklet helloWorldTasklet;

    public BatchConfiguration(HelloWorldTasklet helloWorldTasklet) {
        if (helloWorldTasklet == null) {
            throw new IllegalArgumentException("HelloWorldTasklet cannot be null");
        }
        this.helloWorldTasklet = helloWorldTasklet;
    }

    @Bean
    public Job helloWorldJob(JobRepository jobRepository, Step helloWorldStep) {
        // return jobBuilderFactory.get("helloWorldJob")
        //         .start(helloWorldStep)
        //         .preventRestart() // これを削除するか `allowStartIfComplete(true)` を使用
        //         .build();
        return new JobBuilder("helloWorldJob", jobRepository)
            .start(helloWorldStep)
            .build();
    }

    @Bean
    public Step helloWorldStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(helloWorldTasklet, transactionManager)
                .build();
    }
}
