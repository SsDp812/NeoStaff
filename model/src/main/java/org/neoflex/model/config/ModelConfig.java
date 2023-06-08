package org.neoflex.model.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("org.neoflex.model")
public class ModelConfig {
}
