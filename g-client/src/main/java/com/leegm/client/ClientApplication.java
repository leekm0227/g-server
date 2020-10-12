package com.leegm.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;


@SpringBootApplication
@EnableScheduling
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    public static void httpServerTest() {
        HttpServer.create().port(8080).route(routes -> routes.get("/test/{param}", (request, reponse) -> reponse.sendString(request.receive().asString()))).bindNow();
        HttpClient.create().port(8080).get().uri("/test/aaa").responseContent().aggregate().asString().block();
    }
}