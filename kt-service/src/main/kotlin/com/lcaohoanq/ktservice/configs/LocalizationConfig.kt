package com.lcaohoanq.ktservice.configs

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*


@Configuration
class LocalizationConfig {
    @Bean
    fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("i18n.messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    fun customLocaleResolver(): LocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.forLanguageTag("jp"))
        return slr
    }
}