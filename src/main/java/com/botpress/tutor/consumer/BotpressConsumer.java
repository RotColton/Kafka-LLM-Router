package com.botpress.tutor.consumer;

import com.botpress.tutor.model.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotpressConsumer extends AbstractLLMConsumer{

    @Autowired
    public BotpressConsumer(WebClient.Builder webClientBuilder,
                            @Value("${llm.botpress.url}") String llmUrl,
                            @Value("${llm.botpress.topic}") String topicName) {
        super(webClientBuilder, llmUrl, topicName);
    }

    @KafkaListener(topics = "${llm.botpress.topic}", groupId = "llm-group")
    public void consumeBotpressMessage(MessageDTO messageDTO, Acknowledgment acknowledgment) {
        // Ahora podemos llamar al método común de consumo desde la clase abstracta
        consumeMessage(messageDTO, acknowledgment);
    }

    @Override
    public void handleResponse(MessageDTO originalMessage, String response) {
        // Lógica específica para manejar la respuesta de Botpress
        System.out.println("Respuesta de Botpress para el mensaje " + originalMessage.getText() + ": " + response);
        // Podrías aquí publicar la respuesta a otro topic para que se envíe al usuario
    }
}
