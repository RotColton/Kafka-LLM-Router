package com.botpress.tutor.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collections;
import java.util.Map;

import static java.lang.System.*;


@Service
public class KafkaResponseStreamService {

    private final Flux<String> responseFlux;

    public KafkaResponseStreamService(
            @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId,
            @Value("${kafka.topic.response}") String responseTopic) {

        Map<String, Object> consumerProps = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                ConsumerConfig.GROUP_ID_CONFIG, groupId,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName(),
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()
        );

        ReceiverOptions<Object, Object> options = ReceiverOptions.create(consumerProps)
                .subscription(Collections.singletonList(responseTopic));

        this.responseFlux = KafkaReceiver.create(options)
                .receive()
                .map(KafkaResponseStreamService::apply)
                .doOnError(e -> err.println("Error en Kafka Stream: " + e.getMessage()));
    }

    private static String apply(ReceiverRecord<Object, Object> record) {
        return record.value().toString();
    }

    public Flux<String> streamResponses() {
        return responseFlux;
    }
}
