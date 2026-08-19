package com.hooni.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value("${app.images.path}")
    private String imagesBasePath;

    @Autowired
    private SessionMetricsInterceptor sessionMetricsInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String formattedPath = imagesBasePath.endsWith("/") ? imagesBasePath : imagesBasePath + "/";

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + formattedPath, "classpath:/static/images/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionMetricsInterceptor);
    }
}