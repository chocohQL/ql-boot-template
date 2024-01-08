package com.chocoh.ql.constant;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chocoh
 */
public enum RejectPolicy {
    /**
     * 调用者运行策略
     */
    CALLER_RUNS_POLICY(new ThreadPoolExecutor.CallerRunsPolicy()),
    /**
     * 中止策略（默认）
     */
    ABORT_POLICY(new ThreadPoolExecutor.AbortPolicy()),
    /**
     * 抛弃策略
     */
    DISCARD_POLICY(new ThreadPoolExecutor.DiscardPolicy()),
    /**
     * 抛弃最旧策略
     */
    DISCARD_OLD_POLICY(new ThreadPoolExecutor.DiscardOldestPolicy());


    private RejectedExecutionHandler rejectedExecutionHandler;

    RejectPolicy(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    @Override
    public String toString() {
        return this.rejectedExecutionHandler.toString();
    }
}
