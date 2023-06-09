package org.neoflex.business.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("telegram.properties")
public class TelegramConfig {
    @Value("${bot.name}") String botName;
    @Value("${bot.token}") String token;
}