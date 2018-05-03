package org.ljl.look.message.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    Queue messageQueue() {
        return new Queue(ConstConfig.QUEUE_MESSAGE);
    }

    @Bean
    Queue joinMessageQueue() {
        return new Queue(ConstConfig.QUEUE_JOIN_MESSAGE);
    }
}
