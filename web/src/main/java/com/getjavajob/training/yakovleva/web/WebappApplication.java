package com.getjavajob.training.yakovleva.web;

import com.getjavajob.training.yakovleva.config.DaoConfig;
import com.getjavajob.training.yakovleva.service.config.ServiceConfig;
import com.getjavajob.training.yakovleva.web.config.WebConfig;
import com.getjavajob.training.yakovleva.web.security.SecurityConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@Import(value = {
        SecurityConfig.class,
        WebConfig.class,
        ServiceConfig.class,
        DaoConfig.class
})
public class WebappApplication extends SpringBootServletInitializer {
    private static final Logger logger = LogManager.getLogger(WebappApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info("create WebappApplication");
        return application.sources(WebappApplication.class);
    }

}