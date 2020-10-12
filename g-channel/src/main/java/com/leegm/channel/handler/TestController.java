package com.leegm.channel.handler;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/{id}")
    private Flux<String> getTests(@PathVariable String id) {
        return Flux.just("test" + id);
    }
}
