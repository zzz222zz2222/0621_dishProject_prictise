package com.njzpc;/*
 * @author Jiang longteng
 * @date 2025/7/24 11:24
 * */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication

@ServletComponentScan(basePackages = {"com.njzpc.zpccontroller","com.njzpc.filter"})

public class BootApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BootApp.class, args);
    }

// todo
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/C:/software/t/0721/src/main/resources/static/upload/");
    }
}
