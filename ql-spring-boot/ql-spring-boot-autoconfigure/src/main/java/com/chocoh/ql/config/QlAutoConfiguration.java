package com.chocoh.ql.config;

import com.chocoh.ql.async.AsyncServiceManager;
import com.chocoh.ql.properties.AsyncProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chocoh
 */
@Configuration
@EnableConfigurationProperties(AsyncProperties.class)
public class QlAutoConfiguration {
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Bean
    public AsyncServiceManager asyncServiceManager(AsyncProperties asyncProperties) {
        return new AsyncServiceManager(platformTransactionManager).executorBuilder()
                .setMaxPoolSize(asyncProperties.getMaxPoolSize())
                .setCorePoolSize(asyncProperties.getCorePoolSize())
                .setQueueCapacity(asyncProperties.getCorePoolSize())
                .setKeepAliveSeconds(asyncProperties.getKeepAliveSeconds())
                .setThreadNamePrefix(asyncProperties.getThreadNamePrefix())
                .setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy())
                .build();
    }
}
