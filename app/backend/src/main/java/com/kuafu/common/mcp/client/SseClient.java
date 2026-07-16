package com.kuafu.common.mcp.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SseClient {

    private final OkHttpClient httpClient;

    private static final Pattern EVENT_DATA_PATTERN = Pattern.compile("^data:(.+)$", Pattern.MULTILINE);
    private static final Pattern EVENT_ID_PATTERN = Pattern.compile("^id:(.+)$", Pattern.MULTILINE);

    private static final Pattern EVENT_TYPE_PATTERN = Pattern.compile("^event:(.+)$", Pattern.MULTILINE);

    private EventSource sseEventSource;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SseEvent {
        private String id;
        private String type;
        private String data;
    }

    public interface SseEventHandler {
        void onEvent(SseEvent event);

        void onError(Throwable error);
    }

    public SseClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.MINUTES)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build();
    }

    public SseClient(OkHttpClient okHttpClient) {
        this.httpClient = okHttpClient;
    }

    public void subscribe(String url, SseEventHandler eventHandler) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        StringBuilder eventBuilder = new StringBuilder();
        AtomicReference<String> currentEventId = new AtomicReference<>();
        AtomicReference<String> currentEventType = new AtomicReference<>("message");

        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, Response response) {
                log.info("connection open");
            }

            @Override
            public void onEvent(EventSource eventSource, @Nullable String id, @Nullable String type, String line) {

                if (StringUtils.isEmpty(line)) {
                    if (eventBuilder.length() > 0) {
                        String eventData = eventBuilder.toString();
                        SseEvent event = new SseEvent(currentEventId.get(), currentEventType.get(), eventData.trim());
                        eventHandler.onEvent(event);
                        eventBuilder.setLength(0);
                    }
                } else {
                    if (line.startsWith("data:")) {
                        Matcher matcher = EVENT_DATA_PATTERN.matcher(line);
                        if (matcher.find()) {
                            eventBuilder.append(matcher.group(1).trim()).append("\n");
                        }
                    } else if (line.startsWith("id:")) {
                        Matcher matcher = EVENT_ID_PATTERN.matcher(line);
                        if (matcher.find()) {
                            currentEventId.set(matcher.group(1).trim());
                        }
                    } else if (line.startsWith("event:")) {
                        Matcher matcher = EVENT_TYPE_PATTERN.matcher(line);
                        if (matcher.find()) {
                            currentEventType.set(matcher.group(1).trim());
                        }
                    } else {
                        if (StringUtils.startsWith(line, "{")) {
                            //如果是 { 开始就是 数据
                            SseEvent event = new SseEvent("", "message", line);
                            eventHandler.onEvent(event);
                            eventBuilder.setLength(0);
                        } else {
                            // 不是，那就是 endpoint
                            SseEvent event = new SseEvent("", "endpoint", line);
                            eventHandler.onEvent(event);
                            eventBuilder.setLength(0);
                        }
                    }
                }
            }

            @Override
            public void onClosed(EventSource eventSource) {
                if (eventBuilder.length() > 0) {
                    String eventData = eventBuilder.toString();
                    SseEvent event = new SseEvent(currentEventId.get(), currentEventType.get(), eventData.trim());
                    eventHandler.onEvent(event);
                }
            }

            @Override
            public void onFailure(EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                eventHandler.onError(t);
            }
        };

        EventSource.Factory factory = EventSources.createFactory(httpClient);
        this.sseEventSource = factory.newEventSource(request, listener);
    }

    public void close() {
        if (this.sseEventSource != null) {
            this.sseEventSource.cancel();
            this.sseEventSource = null;
        }
    }

}
