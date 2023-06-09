package org.neoflex.application.config;

import org.neoflex.business.config.BusinessConfig;
import org.neoflex.controller.config.ControllerConfig;
import org.neoflex.model.config.ModelConfig;
import org.neoflex.repository.config.RepositoryConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ControllerConfig.class, BusinessConfig.class, RepositoryConfig.class, ModelConfig.class})
public class AppConfig {
}