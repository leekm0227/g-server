package com.leegm.api;

import com.leegm.common.util.Dispatcher;
import com.leegm.common.util.ProtocolEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongoAuditing
@EnableReactiveMongoRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Dispatcher dispatcher() {
        return new Dispatcher();
    }

    @Bean
    public ProtocolEncoder protocolEncoder() {
        return new ProtocolEncoder();
    }
}