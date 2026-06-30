package com.hooni.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {
    @Value("${hooni.image.news-path}")
    private String newsPath;

    @Value("${hooni.image.food-path}")
    private String foodPath;

    @Value("${hooni.image.product-path}")
    private String productPath;

    @Value("${hooni.image.shares-path}")
    private String sharesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/news/**")
                .addResourceLocations("file:///" + newsPath + "/");

        registry.addResourceHandler("/images/products/**")
                .addResourceLocations("file:///" + productPath + "/");

        registry.addResourceHandler("/images/foods/**")
                .addResourceLocations("file:///" + foodPath + "/");

        registry.addResourceHandler("/images/shares/**")
                .addResourceLocations("file:///" + sharesPath + "/");
    }
}