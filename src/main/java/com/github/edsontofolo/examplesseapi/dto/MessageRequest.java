package com.github.edsontofolo.examplesseapi.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String user;
    private String message;
}
