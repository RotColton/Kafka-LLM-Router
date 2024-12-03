package com.botpress.tutor.service;

import com.botpress.tutor.model.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BotService {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public BotService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<MessageDTO> sendMessageToBot(MessageDTO messageDTO, String llmType) {
        String baseUrl = determineBaseUrl(llmType);

        WebClient webClient = webClientBuilder.baseUrl(baseUrl).build();

        return webClient.post()
                .uri("/send")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(messageDTO)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> new MessageDTO("text", response));
    }

    private String determineBaseUrl(String llmType) {
        return switch (llmType.toLowerCase()) {
            case "botpress" -> "http://botpress-url.com";
            case "openai" -> "https://api.openai.com/v1";
            case "custom" -> "http://custom-llm-url.com";
            default -> throw new IllegalArgumentException("Tipo de LLM no soportado: " + llmType);
        };
    }

}
