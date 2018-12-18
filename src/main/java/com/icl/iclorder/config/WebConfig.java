package com.icl.iclorder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Profile("local")
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:7078")
            .allowCredentials(true)
            .allowedHeaders("env", "Authorization", "Origin", "X-Requested-With", "Content-Type", "Accept")
            .allowedMethods("GET","POST", "PUT", "DELETE", "OPTIONS")
        ;
    }
}
