package com.carparking.application.config;

import com.carparking.core_auth.config.EnableCoreAuth;
import com.carparking.core_entity.config.EnableAudit;
import com.carparking.core_swagger.config.EnableCoreSwagger;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableAudit
@EnableJpaRepositories("com.carparking.application.repository")
@EnableTransactionManagement
@EnableCoreAuth
@EnableCoreSwagger
@EntityScan("com.carparking.*")
public class ApiApplication {
    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(
                connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
        return factory;
    }

}
