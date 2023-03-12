package com.java.threadpool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfiguration {

    @Bean(name = "threadPoolTaskExecutor1")
    public Executor threadPoolTaskExecutor1() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Integer.MAX_VALUE);
//        executor.setMaxPoolSize(100);
        //executor.setKeepAliveSeconds(1);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setThreadNamePrefix("threadPoolTaskExecutor1-");
        return executor;
    }

}
