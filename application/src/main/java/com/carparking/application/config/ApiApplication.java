package com.carparking.application.config;

import com.carparking.core_entity.config.EnableAudit;
import com.carparking.core_auth.config.EnableCoreAuth;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@EnableAudit
@EnableCoreAuth
@EnableJpaRepositories("com.carparking.application.repository")
@EnableTransactionManagement
public class ApiApplication {
    @Bean
    LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

}
