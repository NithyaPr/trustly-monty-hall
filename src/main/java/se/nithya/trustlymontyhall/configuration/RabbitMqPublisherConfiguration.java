package se.nithya.trustlymontyhall.configuration;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqPublisherConfiguration {

    @Value("${rabbitmq.montyhall.topic.exchange}")
    private String montyHallExchangeName;

    @Value("${rabbitmq.montyhall.publisher.routingkey}")
    private String montyHallRoutingKey;

    @Bean
    public TopicExchange getMontyHallExchange() {
        return new TopicExchange(montyHallExchangeName, true, false);
    }

    public String getMontyHallRoutingKey() {
        return montyHallRoutingKey;
    }

}
