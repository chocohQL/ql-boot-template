package com.chocoh.ql.Manager;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author chocoh
 */
public class TaskServiceManager {

    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private final int OPERATE_DELAY_TIME = 10;
    public TaskServiceManager(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    public void execute(Runnable task) {
        threadPoolTaskExecutor.execute(task);
    }

    public static class ExecutorBuilder {
        private int corePoolSize = 1;
        private int maxPoolSize = Integer.MAX_VALUE;
        private int queueCapacity = Integer.MAX_VALUE;
        private int keepAliveSeconds = 60;
        private String threadNamePrefix;
        private RejectedExecutionHandler rejectedExecutionHandler;

        public TaskServiceManager build() {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setMaxPoolSize(this.maxPoolSize);
            threadPoolTaskExecutor.setCorePoolSize(this.corePoolSize);
            threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity);
            threadPoolTaskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
            threadPoolTaskExecutor.setRejectedExecutionHandler(this.rejectedExecutionHandler);
            threadPoolTaskExecutor.setThreadNamePrefix(this.threadNamePrefix);
            threadPoolTaskExecutor.initialize();
            return new TaskServiceManager(threadPoolTaskExecutor);
        }

        public ExecutorBuilder setThreadNamePrefix(String threadNamePrefix) {
            this.threadNamePrefix = threadNamePrefix;
            return this;
        }

        public ExecutorBuilder setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public ExecutorBuilder setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
            return this;
        }

        public ExecutorBuilder setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
            return this;
        }

        public ExecutorBuilder setKeepAliveSeconds(int keepAliveSeconds) {
            this.keepAliveSeconds = keepAliveSeconds;
            return this;
        }

        public ExecutorBuilder setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
            return this;
        }
    }
}
