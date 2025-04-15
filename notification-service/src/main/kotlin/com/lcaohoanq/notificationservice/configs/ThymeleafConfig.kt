package com.lcaohoanq.notificationservice.configs

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class ThymeleafConfig {

    @Bean(name = ["customTemplateEngine"])
    fun customTemplateEngine(): TemplateEngine {
        val templateResolver = ClassLoaderTemplateResolver().apply {
            prefix = "emails/"
            suffix = ".html"
            templateMode = TemplateMode.HTML
            characterEncoding = "UTF-8"
            checkExistence = true
        }

        return TemplateEngine().apply {
            setTemplateResolver(templateResolver)
        }
    }

}