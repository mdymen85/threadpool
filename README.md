# Thread Pool Tests

## Introduction

The main goal of this project is to study how threads works in Java and how can be optimized. 
All the study are done with endpoints, and jmeter.

In the middle of the request process, I coded a *Thread.sleep* in order to randomize the execution.

## Technologies

- Java 17
- Spring Boot 3.0.4

### Synchronous 

In the first file **threads_1.txt** we can see that in default configurations java runs 200 at same time. In the jmeter 
file, we started with 300 threads. But when the applications achieved 200 threads at the same time, stopped to process 
until a thread will be free.

The longest process: 
*Start time - End time = 10.017985900698633 (sec)*

### Asynchronous

#### threads_2.txt

In the file **threads_2.txt** we can see that all the 300 threads executed instantly, but the amount of threads that
executed in parallel are very low.

The longest process:
*Start time - End time = 16.18498095989369 (sec)*

This happened without any configuration of any Thread Pool.

[Spring JAVA DOC Api: ThreadPoolTaskExecutor](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/concurrent/ThreadPoolTaskExecutor.html)

**The default configuration is a core pool size of 1**, *with unlimited max pool size and unlimited queue capacity. This is
roughly equivalent to Executors.newSingleThreadExecutor(), sharing a single thread for all tasks. Setting 
"queueCapacity" to 0 mimics Executors.newCachedThreadPool(), with immediate scaling of threads in the pool 
to a potentially very high number. Consider also setting a "maxPoolSize" at that point, as well as possibly 
a higher "corePoolSize" (see also the "allowCoreThreadTimeOut" mode of scaling).
NOTE: This class implements Spring's TaskExecutor interface as well as the Executor interface, with the 
former being the primary interface, the other just serving as secondary convenience. For this reason, 
the exception handling follows the TaskExecutor contract rather than the Executor contract, in particular 
regarding the TaskRejectedException.
For an alternative, you may set up a ThreadPoolExecutor instance directly using constructor injection, 
or use a factory method definition that points to the Executors class. To expose such a raw Executor as a 
Spring TaskExecutor, simply wrap it with a ConcurrentTaskExecutor adapter.*

### threads_3.txt

With the following configurations of the Thread Pool in order to execute in @Async:

```
    @Bean(name = "threadPoolTaskExecutor1")
    public Executor threadPoolTaskExecutor1() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("threadPoolTaskExecutor1-");
        return executor;
    }
```

We can see in the file that we initialize all the tasks, and the executed one at a time. Ending:
*Start time - End time = 69370 (millis)* for just 10 parallel requests.

### threads_4.txt

With:

```
    @Bean(name = "threadPoolTaskExecutor1")
    public Executor threadPoolTaskExecutor1() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("threadPoolTaskExecutor1-");
        return executor;
    }
```
means that *setMaxPoolSize* get INT.MAX_VALUE, and *setCorePoolSize* that is the number of threads of the pool, finished
the whole process in:
*Start time - End time = 17.55855784541837 (sec)* for 300 parallel requests.
