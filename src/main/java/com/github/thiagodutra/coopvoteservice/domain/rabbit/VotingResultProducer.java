package com.github.thiagodutra.coopvoteservice.domain.rabbit;



import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingResultProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Queue queue;

    public void produceMessage(String message){
        rabbitTemplate.convertAndSend(queue.getName(), message);
    }
}
