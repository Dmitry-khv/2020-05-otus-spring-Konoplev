package ru.otus.hw2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.hw2.service.IOService;
import ru.otus.hw2.service.IOServiceImpl;


@Configuration
public class AppConfig {

    @Bean
    public IOService ioService() {
        return new IOServiceImpl(System.in, System.out);
    }
}
