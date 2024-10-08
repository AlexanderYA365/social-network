package com.getjavajob.training.yakovleva.service.config;

import com.getjavajob.training.yakovleva.config.DaoConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.getjavajob.training.yakovleva.service")
@Import(DaoConfig.class)
@EnableTransactionManagement
public class ServiceConfig {
}