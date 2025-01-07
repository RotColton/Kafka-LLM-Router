package com.botpress.tutor.service.consumer;

import com.botpress.tutor.model.MessageDTO;
import com.botpress.tutor.service.LlmService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final LlmService llmService; // Servicio para interactuar con el LLM
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.response}")
    private String responseTopic;

    public KafkaConsumerService(LlmService llmService, KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.llmService = llmService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topic.request}", groupId = "backend-group")
    public void consume(String messageJson) {
        try {
            // Deserializar el mensaje recibido
            MessageDTO frontendMessage = objectMapper.readValue(messageJson, MessageDTO.class);

            // Redirigir al LLM correspondiente
            String response = llmService.processMessage(frontendMessage.getMessage(), frontendMessage.getLlmType());
            System.out.println("Respuesta del LLM (" + frontendMessage.getLlmType() + "): " + response);

        } catch (Exception e) {
            System.err.println("Error al procesar el mensaje: " + e.getMessage());
        }
    }
}
