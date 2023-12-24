package com.chocoh.ql.framework.config;

import com.chocoh.ql.common.utils.AsyncServiceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chocoh
 */
@Configuration
public class AsyncConfig {
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public AsyncServiceManager asyncServiceManager() {
        return new AsyncServiceManager(platformTransactionManager).executorBuilder()
                .setMaxPoolSize(10)
                .setCorePoolSize(10)
                .setQueueCapacity(100)
                .setKeepAliveSeconds(100)
                .setThreadNamePrefix("test-AsyncTaskThread-")
                .setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }
}
