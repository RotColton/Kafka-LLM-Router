package com.botpress.tutor.controller;

import com.botpress.tutor.model.MessageDTO;
import com.botpress.tutor.service.producer.KafkaProducerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/messages")
public class MessageController { //El controlador expone un endpoint que el frontend puede usar para enviar mensajes al backend.

    private final KafkaProducerService producerService;

    public MessageController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public Mono<Void> sendMessage(@RequestBody MessageDTO messageDTO) {
        producerService.sendMessage(messageDTO);
        return Mono.empty();
    }
}
