package com.github.edsontofolo.examplesseapi.sse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

public interface NotificationService {
    SseEmitter register(String user, SseEmitter sseEmitter) throws IOException;
    void send(MessageNotification messageNotification);
}
