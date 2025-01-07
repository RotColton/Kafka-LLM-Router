package com.botpress.tutor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class LlmService {

    private final Map<String, String> llmUrls;
    private final WebClient webClient;

    public LlmService(WebClient.Builder webClientBuilder,
                      @Value("${llm.botpress.url}") String botpressUrl,
                      @Value("${llm.openai.url}") String openaiUrl) {
        // Configurar URLs de los LLM
        this.llmUrls = Map.of(
                "botpress", botpressUrl,
                "openai", openaiUrl
        );

        this.webClient = webClientBuilder.build();
    }

    public String processMessage(String message, String llmType) {
        String llmUrl = llmUrls.get(llmType);
        if (llmUrl == null) {
            throw new IllegalArgumentException("Tipo de LLM no soportado: " + llmType);
        }
        return webClient.post()
                .uri(llmUrl)
                .bodyValue(message)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
