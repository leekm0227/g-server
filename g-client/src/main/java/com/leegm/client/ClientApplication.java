package com.leegm.client;

import com.leegm.common.util.ProtocolEncoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;


@SpringBootApplication
@EnableScheduling
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public ProtocolEncoder protocolEncoder() {
        return new ProtocolEncoder();
    }
}