package com.leegm.session.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SessionTask {


    @PostConstruct
    void init() {
    }

    @Scheduled(fixedRate = 1000)
    public void task() {
    }
}



