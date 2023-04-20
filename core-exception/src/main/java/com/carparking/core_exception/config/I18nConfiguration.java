package com.carparking.core_exception.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Configuration
@Slf4j
public class I18nConfiguration {

  @Value("${application.messages.basenames:messages}")
  private String[] baseNames;

  @Bean
  @ConditionalOnMissingBean
  public LocaleResolver localeResolver() {
    CustomLocalResolver localResolver = new CustomLocalResolver();
    localResolver.setDefaultLocale(new Locale("vi", "VN"));
    return localResolver;
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames(baseNames);
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
