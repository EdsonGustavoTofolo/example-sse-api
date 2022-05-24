package com.github.edsontofolo.examplesseapi.user;

import com.github.edsontofolo.examplesseapi.stages.StageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final StageService stageFirstService;

    public UserServiceImpl(@Qualifier("firstStage") StageService stageFirstService) {
        this.stageFirstService = stageFirstService;
    }

    @Override
    public void postMessage(String user, String message) {
        UUID uuid = UUID.randomUUID();
        log.info("Receiving message from {} {}", user, uuid);
        stageFirstService.process(uuid, user, message);
    }
}
