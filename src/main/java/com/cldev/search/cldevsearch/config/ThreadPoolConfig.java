package com.cldev.search.cldevsearch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Copyright © 2018 eSunny Info. Developer Stu. All rights reserved.
 * <p>
 * code is far away from bug with the animal protecting
 * <p>
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @author zpx
 * Build File @date: 2019/9/4 20:43
 * @version 1.0
 * @description
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 100;
    private static final int KEEP_ALIVE_TIME = 10;
    private static final int QUEUE_CAPACITY = 200;
    private static final String THREAD_NAME_PREFIX = "Async-ServerModule-Service-";

    @Bean("asyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /* Number of core threads. When a task is executed,
         if it is less than the core thread,
         a new thread will be opened to execute the task whether there is a free thread or not */
        executor.setCorePoolSize(CORE_POOL_SIZE);
        /* The maximum number of threads,
        when the maximum number of threads is reached and all are working and the cache queue is full,
        the rejection policy is executed */
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        /* Buffer queue */
        executor.setQueueCapacity(QUEUE_CAPACITY);
        /* Thread idle time, used to balance the number of threads */
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        /* Custom thread prefixes */
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        /* Rejection policy:
        the rejected task is executed in the execute method and discarded if the execution thread is closed */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
