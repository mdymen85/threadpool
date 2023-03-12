package com.java.threadpool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class ThreadService {

    private final TimeControlService timeControlService;

    @Async
    public void threadV2(String name, long start, int waitTime) throws InterruptedException {

        Thread.sleep(waitTime);

        var ends = System.currentTimeMillis() - start;

        timeControlService.addTotalExecutionTime(ends);

        log.info("Thread name {} => Total time {}", name, timeControlService.getTotalExecutionTime());

    }

    @Async("threadPoolTaskExecutor1")
    public void threadV3(String name, long start, int waitTime) throws InterruptedException {

        Thread.sleep(waitTime);

        var ends = System.currentTimeMillis() - start;

        timeControlService.addTotalExecutionTime(ends);

        log.info("Thread name {} => Total time {}", name, timeControlService.getTotalExecutionTime());

    }

}
