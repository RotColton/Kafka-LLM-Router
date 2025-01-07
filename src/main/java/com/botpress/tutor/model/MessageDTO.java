package com.botpress.tutor.model;

import lombok.Data;

@Data
public class MessageDTO {

    private String userId;
    private String conversationId;
    private String messageId;
    private String message;
    private String llmType;
}
