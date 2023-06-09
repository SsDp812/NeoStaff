package org.neoflex.business.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan("org.neoflex.business")
@EnableScheduling
public class BusinessConfig {
}
