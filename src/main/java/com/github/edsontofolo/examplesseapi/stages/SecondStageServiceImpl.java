package com.github.edsontofolo.examplesseapi.stages;

import com.github.edsontofolo.examplesseapi.sse.MessageNotification;
import com.github.edsontofolo.examplesseapi.sse.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@Qualifier("secondStage")
@Slf4j
public class SecondStageServiceImpl implements StageService {

    private final StageService stageSecondService;
    private final NotificationService notificationService;

    public SecondStageServiceImpl(@Qualifier("stageFinal")
                                  StageService stageSecondService,
                                  NotificationService notificationService) {
        this.stageSecondService = stageSecondService;
        this.notificationService = notificationService;
    }

    @Override
    public void process(UUID uuid, String user, String message) {
        log.info("Proccessing stage second {}", uuid);

        int milliseconds = ThreadLocalRandom.current().nextInt(1000, 5000);

        try {
            notificationService.send(
                    MessageNotification.builder()
                            .user(user)
                            .stage(StageEnum.SECOND)
                            .message("Processing second stage with message \"" +
                                    message + "\" from " + user + " - " + uuid + "(" + LocalDateTime.now() + ")")
                            .build());

            TimeUnit.valueOf("MILLISECONDS").sleep(milliseconds);

            stageSecondService.process(uuid, user, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
