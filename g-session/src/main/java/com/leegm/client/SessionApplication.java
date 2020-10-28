package com.leegm.client;

import com.leegm.common.util.Dispatcher;
import com.leegm.common.util.ProtocolEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SessionApplication.class, args);
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