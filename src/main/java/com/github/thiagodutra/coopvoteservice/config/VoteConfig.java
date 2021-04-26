package com.github.thiagodutra.coopvoteservice.config;



import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class VoteConfig {

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key")
    private String routingKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Bean
    public Queue queue(){
        return new Queue(queueName, true);
    }
}