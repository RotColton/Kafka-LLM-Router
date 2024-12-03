package com.botpress.tutor.service;

import com.botpress.tutor.model.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RouterService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "incoming.messages", groupId = "llm-group")
    public void routeMessage(MessageDTO messageDTO) {
        String destinationTopic = determineDestination(messageDTO);

        try {
            String messageAsJson = objectMapper.writeValueAsString(messageDTO);
            kafkaTemplate.send(destinationTopic, messageAsJson);
        } catch (Exception e) {
            //TODO: implementar LOGGER
            e.printStackTrace();
        }
    }

    private String determineDestination(MessageDTO messageDTO) {
        return switch (messageDTO.getType()) {
            case "botpress" -> "botpress.messages";
            case "openai" -> "openai.messages";
            default -> "customModel.messages";
        };
    }
}
