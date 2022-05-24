package com.github.edsontofolo.examplesseapi.stages;

import com.github.edsontofolo.examplesseapi.sse.MessageNotification;
import com.github.edsontofolo.examplesseapi.sse.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
@Qualifier("stageFinal")
@RequiredArgsConstructor
@Slf4j
public class FinalStageServiceImpl implements StageService {

    private final NotificationService notificationService;

    @Override
    public void process(UUID uuid, String user, String message) {
        log.info("Proccessing stage final {}", uuid);

        int milliseconds = ThreadLocalRandom.current().nextInt(1000, 6000);

        try {
            notificationService.send(
                    MessageNotification.builder()
                            .user(user)
                            .stage(StageEnum.FINAL_PROCESSING)
                            .message("Processing final stage with message \"" +
                                    message + "\" from " + user + " - " + uuid + "(" + LocalDateTime.now() + ")")
                            .build());

            TimeUnit.valueOf("MILLISECONDS").sleep(milliseconds);

            notificationService.send(
                    MessageNotification.builder()
                            .user(user)
                            .stage(StageEnum.FINAL_SUCCESSFULLY)
                            .message("Final stage successfully processed... " + user + " - " + uuid)
                            .build());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
