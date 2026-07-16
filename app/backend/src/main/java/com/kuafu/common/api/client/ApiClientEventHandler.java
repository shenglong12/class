package com.kuafu.common.api.client;

public interface ApiClientEventHandler {

    WssEventMessage event(String message);
}
