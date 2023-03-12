package com.java.threadpool;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ThreadController {

    private final static Random RANDOM = new Random();
    private static int COUNTER = 0;

    private final TimeControlService timeControlService;

    /**
     * 200 threads
     * @throws InterruptedException
     */
    @RequestMapping(path = "/v1/thread", method = RequestMethod.POST)
    public void thread() throws InterruptedException {

        long start = System.currentTimeMillis();
        int waitTime = nextRandomNumber();
        COUNTER++;
        log.info("Thread name {} => Starts => Waiting {} => Counter {}", Thread.currentThread().getName(), waitTime, COUNTER);
        Thread.sleep(waitTime);

        var ends = System.currentTimeMillis() - start;

        timeControlService.addTotalExecutionTime(ends);

        log.info("Thread name {} => Ends {} => Total time {}", Thread.currentThread().getName(), ends, timeControlService.getTotalExecutionTime());

    }

    private final ThreadService threadService;

    @RequestMapping(path = "/v2/thread", method = RequestMethod.POST)
    public void thread2() throws InterruptedException {
        long start = System.currentTimeMillis();
        int waitTime = nextRandomNumber();
        COUNTER++;
        log.info("Thread name {} => Starts => Waiting {} => Counter {}", Thread.currentThread().getName(), waitTime, COUNTER);

        this.threadService.threadV2(Thread.currentThread().getName(), start, waitTime);

        log.info("Thread name {} => Waiting {} => Counter {}", Thread.currentThread().getName(), waitTime, COUNTER);

    }

    @RequestMapping(path = "/v3/thread", method = RequestMethod.POST)
    public void thread3() throws InterruptedException {
        long start = System.currentTimeMillis();
        int waitTime = nextRandomNumber();
        COUNTER++;
        log.info("Thread name {} => Starts => Waiting {} => Counter {}", Thread.currentThread().getName(), waitTime, COUNTER);

        this.threadService.threadV3(Thread.currentThread().getName(), start, waitTime);

        log.info("Thread name {} => Waiting {} => Counter {}", Thread.currentThread().getName(), waitTime, COUNTER);

    }


    private int nextRandomNumber() {
        int low = 5000;
        int high = 10000;
        return RANDOM.nextInt(high-low) + low;
    }

}
