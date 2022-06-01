package com.sdms.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class MvcConfig {

    @Resource
    private PictureConfig pictureConfig;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
                // 静态资源路径映射
                // 将 http://localhost:8080/sdms-images/** 格式的 URL映射到 pictureConfig.getPath() 这个本地绝对路径下
                registry.addResourceHandler("/sdms-images/**")
                        .addResourceLocations("file:" + pictureConfig.getPath());
            }
        };
    }
}
