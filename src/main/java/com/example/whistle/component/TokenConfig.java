package com.example.whistle.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "discord", ignoreInvalidFields = true)
@Data
public class TokenConfig {
    private BotConfig bot;

    @Data
    public static class BotConfig{
        private String token;
    }
}
