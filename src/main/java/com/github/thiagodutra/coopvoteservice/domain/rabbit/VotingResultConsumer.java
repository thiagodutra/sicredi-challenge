package com.github.thiagodutra.coopvoteservice.domain.rabbit;

import java.time.LocalTime;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VotingResultConsumer {

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(@Payload String message){
      log.info("{} - VotingResultConsumer: Consumed message {}", LocalTime.now().toString(), message);
      System.out.println(String.format("%s - VotingResultConsumer: Consumed message %s", LocalTime.now().toString(), message));
    }    
}
