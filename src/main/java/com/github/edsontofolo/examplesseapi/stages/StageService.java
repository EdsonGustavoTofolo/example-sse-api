package com.github.edsontofolo.examplesseapi.stages;

import java.util.UUID;

public interface StageService {
    void process(UUID uuid, String user, String message);
}
