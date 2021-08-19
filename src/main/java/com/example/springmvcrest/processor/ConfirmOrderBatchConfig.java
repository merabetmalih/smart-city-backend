package com.example.springmvcrest.processor;

import com.example.springmvcrest.order.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class ConfirmOrderBatchConfig {
    private JobBuilderFactory jobs;
    private StepBuilderFactory steps;
    private OrderService orderService;

    @Bean
    public Step confirmReceiveOrder(){
        return steps.get("confirmReceiveOrder")
                .tasklet(new AutoConfirmReceiveOrder(orderService))
                .build();
    }

    @Bean
    public Job demoJob(){
        return jobs.get("demoJob")
                .incrementer(new RunIdIncrementer())
                .start(confirmReceiveOrder())
                .build();
    }
}
