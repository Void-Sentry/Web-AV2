package com.example.jogosdevideogame;

import com.example.jogosdevideogame.Service.FileStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class JogosDeVideoGameApplication implements CommandLineRunner, WebMvcConfigurer {

    @Resource
    FileStorageService storageService;

    public static void main(String[] args){
        SpringApplication.run(JogosDeVideoGameApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        //storageService.deleteAll();
        storageService.init();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/")
                .setCachePeriod(31556926);
    }

}
