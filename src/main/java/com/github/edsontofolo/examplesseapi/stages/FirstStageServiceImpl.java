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
@Qualifier("firstStage")
@Slf4j
public class FirstStageServiceImpl implements StageService {

    private final StageService stageSecondService;
    private final NotificationService notificationService;

    public FirstStageServiceImpl(@Qualifier("secondStage")
                                 StageService stageSecondService,
                                 NotificationService notificationService) {
        this.stageSecondService = stageSecondService;
        this.notificationService = notificationService;
    }

    @Override
    public void process(UUID uuid, String user, String message) {
        log.info("Proccessing stage first {}", uuid);

        int milliseconds = ThreadLocalRandom.current().nextInt(4000, 10000);

        try {
            notificationService.send(
                    MessageNotification.builder()
                            .user(user)
                            .stage(StageEnum.FIRST)
                            .message("Processing first stage with message \"" +
                                    message + "\" from " + user + " - " + uuid + " (" + LocalDateTime.now() + ")")
                            .build());

            TimeUnit.valueOf("MILLISECONDS").sleep(milliseconds);

            stageSecondService.process(uuid, user, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
