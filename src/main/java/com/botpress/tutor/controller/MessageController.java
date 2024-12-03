package com.botpress.tutor.controller;

import com.botpress.tutor.model.dto.MessageDTO;
import com.botpress.tutor.service.MessageProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageProducer messageProducer;

    public MessageController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/sendMessage")
    public Mono<ResponseEntity<Void>> sendMessage(@RequestBody MessageDTO messageDTO) {
        messageProducer.sendMessage(messageDTO);
        return Mono.just(ResponseEntity.ok().build());
    }
}
