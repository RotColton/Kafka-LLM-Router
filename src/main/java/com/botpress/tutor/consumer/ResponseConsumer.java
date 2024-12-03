package com.botpress.tutor.consumer;

import com.botpress.tutor.model.dto.MessageDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ResponseConsumer {

    @KafkaListener(topics = "responses", groupId = "llm-group")
    public void sendResponseToFrontend(MessageDTO responseDTO) {
        // Aquí podrías implementar lógica para enviar la respuesta al frontend a través de un WebSocket u otro método
        System.out.println("Respuesta para enviar al usuario: " + responseDTO.getText());
    }


}
