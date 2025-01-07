package com.botpress.tutor.service.producer;

import com.botpress.tutor.model.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService { //El productor publica los mensajes enviados desde el frontend.

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.request}")
    private String requestTopic;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(MessageDTO messageDTO) {
        //Todo: cambiar para un mejor manejo de la seguridad y las excepciones
        try {
            // Convertir el mensaje a JSON
            String messageJson = objectMapper.writeValueAsString(messageDTO);
            kafkaTemplate.send(requestTopic, messageJson);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el mensaje a Kafka", e);
        }
    }
}
