package com.github.edsontofolo.examplesseapi.sse;

import com.github.edsontofolo.examplesseapi.stages.StageEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageNotification {
    private String user;
    private StageEnum stage;
    private String message;
}
