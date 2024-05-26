package com.example.t1_hw3.config;

import com.example.t1_hw3.filter.MethodLoggingFilter;
import com.example.t1_hw3.logger.MethodDetailsLogger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Конфигурация spring-boot-starter
 */
@AutoConfiguration
@EnableConfigurationProperties(CustomLoggerProperties.class)
@EnableAspectJAutoProxy
@ConditionalOnProperty(
        prefix = "custom-logger",
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true)
public class CustomLoggerConfiguration {

    @Bean("customLogger")
    public MethodDetailsLogger customLogger() {
        return new MethodDetailsLogger();
    }

    @Bean("methodLoggingFilter")
    public MethodLoggingFilter methodLoggingFilter(MethodDetailsLogger methodDetailsLogger) {
        return new MethodLoggingFilter(methodDetailsLogger);
    }

}
