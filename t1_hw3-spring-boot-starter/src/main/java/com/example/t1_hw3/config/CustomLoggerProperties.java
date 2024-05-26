package com.example.t1_hw3.config;

import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Настройки конфигурации spring-boot-starter
 */
@NoArgsConstructor
@Configuration
@ConfigurationProperties("custom-logger")
public class CustomLoggerProperties {
    private boolean enabled;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
