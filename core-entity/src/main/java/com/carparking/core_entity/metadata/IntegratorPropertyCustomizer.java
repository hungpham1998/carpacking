package com.carparking.core_entity.metadata;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.boot.spi.IntegratorProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;

import java.util.Collections;
import java.util.Map;

@Slf4j
public class IntegratorPropertyCustomizer implements HibernatePropertiesCustomizer {
  @Override
  public void customize(Map<String, Object> hibernateProperties) {
    log.info("(customize)add Metadata extractor.");
    hibernateProperties.put(
        "hibernate.integrator_provider",
        (IntegratorProvider) () -> Collections.singletonList(MetadataExtractorIntegrator.INSTANCE)
    );
  }
}
