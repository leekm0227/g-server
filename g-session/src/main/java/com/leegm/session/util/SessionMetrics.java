package com.leegm.session.util;


import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static reactor.netty.Metrics.DATA_SENT;
import static reactor.netty.Metrics.TCP_SERVER_PREFIX;

@Component
public class SessionMetrics {

    private MeterRegistry registry;

    @PostConstruct
    void init() {
        registry = new SimpleMeterRegistry();
        Metrics.addRegistry(registry);
    }

    public void log(String name){
        System.out.println("gauge : " + registry.find(TCP_SERVER_PREFIX + DATA_SENT).gauge().value());
    }

}
