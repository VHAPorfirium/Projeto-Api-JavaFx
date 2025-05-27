package com.ApiRestJavaFx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Essa classe tem a funcionalidade de configurar o CORS e o mapeamento de datas para JSON.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // Define as permissões de CORS permitindo qualquer origem, método e cabeçalho.
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
