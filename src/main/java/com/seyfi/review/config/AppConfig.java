package com.seyfi.review.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Configuration
public class AppConfig {

    @Bean(name = "reviewDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource reviewDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        messageConverters.add(converter);
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

}