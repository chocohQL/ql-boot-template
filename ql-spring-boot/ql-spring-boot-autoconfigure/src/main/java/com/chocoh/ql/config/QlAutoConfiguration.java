package com.chocoh.ql.config;

import com.chocoh.ql.Manager.TaskServiceManager;
import com.chocoh.ql.properties.TaskServiceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chocoh
 */
@Configuration
@EnableConfigurationProperties(TaskServiceProperties.class)
public class QlAutoConfiguration {
    @Bean
    public TaskServiceManager asyncServiceManager(TaskServiceProperties taskServiceProperties) {
        return new TaskServiceManager.ExecutorBuilder()
                .setMaxPoolSize(taskServiceProperties.getMaxPoolSize())
                .setCorePoolSize(taskServiceProperties.getCorePoolSize())
                .setQueueCapacity(taskServiceProperties.getCorePoolSize())
                .setKeepAliveSeconds(taskServiceProperties.getKeepAliveSeconds())
                .setThreadNamePrefix(taskServiceProperties.getThreadNamePrefix())
                .setRejectedExecutionHandler(taskServiceProperties.getRejectPolicy().getRejectedExecutionHandler())
                .build();
    }
}
