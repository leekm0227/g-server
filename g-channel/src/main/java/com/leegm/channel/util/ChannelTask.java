package com.leegm.channel.util;

import com.leegm.channel.publisher.ZonePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ChannelTask {

    @Autowired
    ZonePublisher zonePublisher;

    @Scheduled(fixedRate = 100)
    public void task() {
        zonePublisher.onNext();
    }
}



