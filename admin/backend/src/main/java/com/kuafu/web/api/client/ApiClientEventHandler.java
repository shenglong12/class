package com.kuafu.web.api.client;

public interface ApiClientEventHandler {

    WssEventMessage event(String message);
}
