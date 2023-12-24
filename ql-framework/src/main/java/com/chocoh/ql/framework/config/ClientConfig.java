package com.chocoh.ql.framework.config;

import com.chocoh.ql.common.utils.EmailClient;
import com.chocoh.ql.common.utils.OssClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author chocoh
 */
@Configuration
public class ClientConfig {
    @Bean
    public EmailClient emailClient() {
        return new EmailClient();
    }

    @Bean
    public OssClient ossClient() {
        return new OssClient();
    }
}