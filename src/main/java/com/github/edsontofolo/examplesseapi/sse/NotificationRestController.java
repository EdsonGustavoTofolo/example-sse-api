package com.github.edsontofolo.examplesseapi.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

import static javax.management.timer.Timer.ONE_MINUTE;

@RestController
@RequestMapping("/api/sses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificationRestController {

    private final NotificationService notificationService;

    @GetMapping("/stream/{user}")
    public SseEmitter stream(@PathVariable String user) throws IOException {
        final SseEmitter sseEmitter = new SseEmitter(ONE_MINUTE * 5);
        this.notificationService.register(user, sseEmitter);
        return sseEmitter;
    }
}
