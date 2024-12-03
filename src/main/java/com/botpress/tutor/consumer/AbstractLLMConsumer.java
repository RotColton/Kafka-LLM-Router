package com.botpress.tutor.consumer;

import com.botpress.tutor.model.dto.MessageDTO;
import org.springframework.http.MediaType;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.reactive.function.client.WebClient;


public abstract class AbstractLLMConsumer {


    private final WebClient webClient;
    private final String llmUrl;
    private final String topicName;

    protected AbstractLLMConsumer(WebClient.Builder webClient, String llmUrl, String topicName) {
        this.webClient = webClient.build();
        this.llmUrl = llmUrl;
        this.topicName = topicName;
    }

    protected String getLLMUrl(){
        return this.llmUrl;
    }

    protected String getTopicName(){
        return this.topicName;
    }

    public void consumeMessage(MessageDTO messageDTO, Acknowledgment acknowledgment) {
        String url = getLLMUrl();
        webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(messageDTO)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    handleResponse(messageDTO, response);
                    acknowledgment.acknowledge(); // Acknowledge Kafka after successful handling
                }, error -> {
                    // TODO: implementar LOGGER
                    System.err.println("Error procesando el mensaje: " + error.getMessage());
                });
    }


    public abstract void handleResponse(MessageDTO originalMessage, String response);

}
