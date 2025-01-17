package com.lcaohoanq.shoppe.config;

import graphql.scalars.ExtendedScalars;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(ExtendedScalars.Locale)
            .scalar(ExtendedScalars.LocalTime)
            .scalar(ExtendedScalars.Date)
            .scalar(ExtendedScalars.DateTime);
    }
}