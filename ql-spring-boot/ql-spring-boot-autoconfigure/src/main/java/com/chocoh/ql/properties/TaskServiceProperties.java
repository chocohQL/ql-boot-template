package com.chocoh.ql.properties;

import com.chocoh.ql.constant.RejectPolicy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author chocoh
 */
@Configuration
@ConfigurationProperties(prefix = TaskServiceProperties.QL_PREFIX)
public class TaskServiceProperties {
    static final String QL_PREFIX = "ql.task";

    private Integer corePoolSize = 10;
    private Integer maxPoolSize = Integer.MAX_VALUE;
    private Integer queueCapacity = Integer.MAX_VALUE;
    private Integer keepAliveSeconds = 60;
    private String threadNamePrefix = "ql-TaskService-";
    private RejectPolicy rejectPolicy = RejectPolicy.CALLER_RUNS_POLICY;

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public RejectPolicy getRejectPolicy() {
        return rejectPolicy;
    }

    public void setRejectPolicy(RejectPolicy rejectPolicy) {
        this.rejectPolicy = rejectPolicy;
    }
}
