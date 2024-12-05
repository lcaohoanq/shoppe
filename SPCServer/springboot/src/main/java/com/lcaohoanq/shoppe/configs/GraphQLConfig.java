package com.lcaohoanq.shoppe.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {
    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(jsonScalar());
    }

    @Bean
    public GraphQLScalarType jsonScalar() {
        return GraphQLScalarType.newScalar()
            .name("JSON")
            .description("A JSON scalar")
            .coercing(new Coercing<Object, String>() {
                private final ObjectMapper objectMapper = new ObjectMapper();

                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult == null) {
                        return null;
                    }
                    try {
                        return objectMapper.writeValueAsString(dataFetcherResult);
                    } catch (JsonProcessingException e) {
                        throw new CoercingSerializeException("Cannot serialize JSON", e);
                    }
                }

                @Override
                public Object parseValue(Object input) throws CoercingParseValueException {
                    return input;
                }

                @Override
                public Object parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof StringValue) {
                        return ((StringValue) input).getValue();
                    }
                    return input;
                }
            })
            .build();
    }
}