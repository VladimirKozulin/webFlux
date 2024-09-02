package com.example.webflux.config;

import com.mongodb.internal.VisibleForTesting;
import org.springframework.boot.web.error.ErrorAttributeOptions;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Configuration
public class RouterErrorConfig {
    @Bean
    public DefaultErrorAttributes errorAttributes(){
        return new DefaultErrorAttributes(){
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);

                errorAttributes.put("error-attributes", "Error in request");
                errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());

                return errorAttributes;
            }
        };
    }
}
