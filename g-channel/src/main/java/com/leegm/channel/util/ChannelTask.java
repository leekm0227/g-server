package com.leegm.channel.util;

import com.leegm.channel.publisher.ZonePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ChannelTask {

    private static final Logger logger = LoggerFactory.getLogger(ChannelTask.class);

    @Autowired
    ZonePublisher zonePublisher;

    @Scheduled(fixedRate = 100)
    public void task() {
        if (zonePublisher.isUpdate()) {
            zonePublisher.update();
        }
    }
}



