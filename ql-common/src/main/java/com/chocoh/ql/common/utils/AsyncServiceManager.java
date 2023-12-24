package com.chocoh.ql.common.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author chocoh
 */
public class AsyncServiceManager {
    /**
     * 是否提交
     */
    private final AtomicBoolean isSubmit = new AtomicBoolean(true);

    /**
     * spring任务管理器
     */
    private final PlatformTransactionManager transactionManager;

    /**
     * spring任务线程池
     */
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    /**
     * 线程池建造者
     */
    private final ExecutorBuilder executorBuilder = new ExecutorBuilder(this);

    public AsyncServiceManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public ExecutorBuilder executorBuilder() {
        return this.executorBuilder;
    }

    /**
     * 事务安全的线程池分段插入数据库方法
     */
    public <T> void asyncInsert(List<T> dataList, BaseMapper<T> mapper) {
        if (dataList == null) {
            return;
        }
        if (dataList.isEmpty()) {
            return;
        }
        // 如果没有构建线程池，则使用默认配置
        if (this.threadPoolTaskExecutor == null) {
            this.executorBuilder.build();
        }
        // 计算每个线程需要操作多少条数据
        int totalRows = dataList.size();
        int threadCount = Math.min(threadPoolTaskExecutor.getMaxPoolSize(), totalRows);
        int rowsPerThread = totalRows / threadCount;
        // 执行插入的计时器
        CountDownLatch threadLatch = new CountDownLatch(threadCount);
        // 执行提交的计数器
        CountDownLatch commitLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            // 分段集合，交给每个线程执行
            int startRow = i * rowsPerThread;
            int endRow = (i == threadCount - 1) ? totalRows : startRow + rowsPerThread;
            List<T> subList = dataList.subList(startRow, endRow);
            threadPoolTaskExecutor.execute(() ->
            {
                if (!isSubmit.get()) {
                    threadLatch.countDown();
                    commitLatch.countDown();
                    return;
                }
                // 开启事务
                TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionAttribute());
                try {
                    // 执行插入
                    subList.forEach(mapper::insert);
                } catch (Exception e) {
                    // 抛出异常，提交失败
                    isSubmit.set(false);
                } finally {
                    threadLatch.countDown();
                }
                try {
                    threadLatch.await();
                    // 等待所有线程执行完毕，若没有线程抛出异常，则全部提交，否则全部回滚
                    if (isSubmit.get()) {
                        transactionManager.commit(transaction);
                    } else {
                        transactionManager.rollback(transaction);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    commitLatch.countDown();
                }
            });
        }
        try {
            commitLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 恢复提交状态
            isSubmit.set(true);
        }
    }

    public static class ExecutorBuilder {
        private int corePoolSize = 10;
        private int maxPoolSize = 10;
        private int queueCapacity = 40;
        private int keepAliveSeconds = 100;
        private String threadNamePrefix = "AsyncTaskThread-";
        private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();

        private final AsyncServiceManager asyncServiceManager;

        public ExecutorBuilder(AsyncServiceManager asyncServiceManager) {
            this.asyncServiceManager = asyncServiceManager;
        }

        public AsyncServiceManager build() {
            this.asyncServiceManager.threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            this.asyncServiceManager.threadPoolTaskExecutor.setMaxPoolSize(this.maxPoolSize);
            this.asyncServiceManager.threadPoolTaskExecutor.setCorePoolSize(this.corePoolSize);
            this.asyncServiceManager.threadPoolTaskExecutor.setQueueCapacity(this.queueCapacity);
            this.asyncServiceManager.threadPoolTaskExecutor.setKeepAliveSeconds(this.keepAliveSeconds);
            this.asyncServiceManager.threadPoolTaskExecutor.setRejectedExecutionHandler(this.rejectedExecutionHandler);
            this.asyncServiceManager.threadPoolTaskExecutor.setThreadNamePrefix(this.threadNamePrefix);
            this.asyncServiceManager.threadPoolTaskExecutor.initialize();
            return this.asyncServiceManager;
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
