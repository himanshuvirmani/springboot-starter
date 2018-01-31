package com.core.config;

import com.core.util.datetime.serialization.CustomDateTimeDeserializer;
import com.core.util.datetime.serialization.CustomDateTimeSerializer;
import com.core.util.datetime.serialization.CustomLocalDateSerializer;
import com.core.util.datetime.serialization.ISO8601LocalDateDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by himanshu.virmani on 08/12/15.
 */
@Configuration
public class JacksonConfiguration {

    @Bean
    public JodaModule jacksonJodaModule() {
        JodaModule module = new JodaModule();
        module.addSerializer(DateTime.class, new CustomDateTimeSerializer());
        module.addDeserializer(DateTime.class, new CustomDateTimeDeserializer());
        module.addSerializer(LocalDate.class, new CustomLocalDateSerializer());
        module.addDeserializer(LocalDate.class, new ISO8601LocalDateDeserializer());
        return module;
    }


    @Bean(name = "dateFormatMapper")
    public ObjectMapper objectMapper() {
        // Customize...
        return Jackson2ObjectMapperBuilder.
                json()
                .dateFormat(new SimpleDateFormat("yyyy-dd HH:mm:ss"))
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .timeZone(TimeZone.getTimeZone("Asia/Calcutta"))
                .modules(new JodaModule())
                .build();
    }

}
