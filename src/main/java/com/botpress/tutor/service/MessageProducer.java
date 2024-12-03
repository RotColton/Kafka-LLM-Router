package com.botpress.tutor.service;

import com.botpress.tutor.model.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer{

    private final KafkaTemplate<Object, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageProducer(KafkaTemplate<Object, String> template) {
        this.kafkaTemplate = template;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(MessageDTO messageDTO) {
        String messageJson = null;
        try {
            messageJson = objectMapper.writeValueAsString(messageDTO);
        } catch (JsonProcessingException e) {
            //TODO: cambiar a logger
            e.printStackTrace();
        }
        kafkaTemplate.send("incoming.messages", messageJson);
    }
}
