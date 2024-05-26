package com.example.t1_hw3.api;

import com.example.t1_hw3.config.CustomLoggerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

class MethodLoggerClientTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
    .withConfiguration(AutoConfigurations.of(CustomLoggerConfiguration.class, RestClientAutoConfiguration.class));

    @Test
    void ShouldContainBeans() {
        contextRunner.run(context -> {
            assertTrue(context.containsBean("customLogger"));
            assertTrue(context.containsBean("methodLoggingFilter"));
        });
    }

}