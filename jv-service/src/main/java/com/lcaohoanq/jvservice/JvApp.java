package com.lcaohoanq.jvservice;


import com.lcaohoanq.common.utils.WebUtil;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableCaching
@EnableRetry
public class JvApp {

    public static void main(String[] args) {
        // Set default timezone
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        // Run Spring Boot application
        SpringApplication.run(JvApp.class, args);

        // Open home pages in browser
//        WebUtil.INSTANCE.openHomePage(WebUtil.INSTANCE.getUrlListJava());
    }
}