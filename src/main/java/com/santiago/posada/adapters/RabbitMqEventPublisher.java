package com.santiago.posada.adapters;

import com.santiago.posada.configuration.RabbitMqConfig;
import com.santiago.posada.repository.model.ToDo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.OutboundMessageResult;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqEventPublisher {

    private final Sender sender;

    public RabbitMqEventPublisher(Sender sender) {
        this.sender = sender;
    }

    public Flux<OutboundMessageResult> publishTaskCreated(ToDo toDo){
        return sender.sendWithPublishConfirms(
                Mono.just(new OutboundMessage(RabbitMqConfig.TOPIC_EXCHANGE,
                        RabbitMqConfig.ROUTING_KEY_EVENTS_TASK_CREATED,
                        toDo.toString().getBytes())));
    }
}
