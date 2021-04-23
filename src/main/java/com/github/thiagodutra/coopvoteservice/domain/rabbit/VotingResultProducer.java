package com.github.thiagodutra.coopvoteservice.domain.rabbit;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class VotingResultProducer {

    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    public void produceMessage(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }

}
