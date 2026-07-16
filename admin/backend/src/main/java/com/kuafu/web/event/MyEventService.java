package com.kuafu.web.event;

import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyEventService {

    private final EventBus eventBus;

    @Autowired
    public MyEventService(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void publishEvent(EventVo event) {
        eventBus.post(event);
    }
}
