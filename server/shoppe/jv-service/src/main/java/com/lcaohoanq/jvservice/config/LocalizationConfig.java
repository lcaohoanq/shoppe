package com.lcaohoanq.jvservice.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class LocalizationConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n.messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

   @Bean
   public LocaleResolver customLocaleResolver() {
       SessionLocaleResolver slr = new SessionLocaleResolver();
       slr.setDefaultLocale(Locale.forLanguageTag("en"));
       return slr;
   }

}