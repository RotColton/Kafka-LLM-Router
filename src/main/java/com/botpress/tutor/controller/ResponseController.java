package com.botpress.tutor.controller;

import com.botpress.tutor.service.KafkaResponseStreamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {

    private final KafkaResponseStreamService responseStreamService;

    public ResponseController(KafkaResponseStreamService responseStreamService) {
        this.responseStreamService = responseStreamService;
    }

    // Exponer el flujo reactivo como SSE
    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponses() {
        return responseStreamService.streamResponses();
    }
}
