package com.hooni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Freemarker configuration for Spring Boot.
 *
 * Original setup used WebappTemplateLoader pointing at the webapp's root
 * directories (one per virtual host: www, food, wigs).
 * Spring Boot loads templates from classpath:/templates/ — place your .ftl
 * files there.  Sub-directories are supported: food/foods_home.ftl, etc.
 */
@Configuration
public class FreemarkerConfig {

    @Bean
    public FreeMarkerConfigurer freemarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        // Mirrors the original: load from classpath:/templates/ + classpath:/templates/include/
        configurer.setTemplateLoaderPaths("classpath:/templates/", "classpath:/templates/include/");
        configurer.setDefaultEncoding("UTF-8");

        Map<String, Object> sharedVariables = new HashMap<>();
        // Any global template variables that all views need can be added here.

        Properties freemarkerSettings = new Properties();
        freemarkerSettings.put("default_encoding", "UTF-8");
        freemarkerSettings.put("output_encoding", "UTF-8");
        freemarkerSettings.put("locale", "en_US");
        freemarkerSettings.put("template_exception_handler", "rethrow");
        configurer.setFreemarkerSettings(freemarkerSettings);

        return configurer;
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(false); // set true in production
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setOrder(1);
        return resolver;
    }
}
