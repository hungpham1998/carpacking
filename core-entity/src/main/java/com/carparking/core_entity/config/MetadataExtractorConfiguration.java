package com.carparking.core_entity.config;

import com.carparking.core_entity.metadata.IntegratorPropertyCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetadataExtractorConfiguration {
  @Bean
  HibernatePropertiesCustomizer metadataExtractorInjector() {
    return new IntegratorPropertyCustomizer();
  }
}
