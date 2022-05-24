package com.github.edsontofolo.examplesseapi.sse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final Map<String, List<SseEmitter>> userEmitters = Collections.synchronizedMap(new HashMap<>());

    @Override
    public SseEmitter register(String user, SseEmitter emitter) throws IOException {
        log.info("Connecting {}", user);
        emitter.onCompletion(() -> complete(user, emitter));
        emitter.onTimeout(() -> timeout(user, emitter));
        emitter.onError(throwable -> error(user, emitter, throwable));

        emitter.send(SseEmitter.event().id(user).comment("Stablished connection successfully").name("onopen"));

        List<SseEmitter> emitters = this.userEmitters.computeIfAbsent(user, s ->
                Collections.synchronizedList(new LinkedList<>()));
        emitters.add(emitter);

        return emitter;
    }

    private void complete(String user, SseEmitter sseEmitter) {
        log.info("Completing emitter");
        remove(user, sseEmitter);
    }
    private void timeout(String user, SseEmitter sseEmitter) {
        log.info("Timeout emitter");
        remove(user, sseEmitter);
    }
    private void error(String user, SseEmitter sseEmitter, Throwable throwable) {
        log.error("Error emitter", throwable);
        remove(user, sseEmitter);
    }

    private void remove(String user, SseEmitter sseEmitter) {
        List<SseEmitter> emitters = this.userEmitters.get(user);
        emitters.remove(sseEmitter);
    }

    @Override
    public void send(MessageNotification messageNotification) {
        log.info("Notifying {}", messageNotification.getUser());

        List<SseEmitter> emitters = this.userEmitters.get(messageNotification.getUser());

        List<SseEmitter> emittersFailed = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(messageNotification);
            } catch (IOException e) {
                emitter.completeWithError(e);
                emittersFailed.add(emitter);
            }
        }
        emitters.removeAll(emittersFailed);
    }
}
