package se.nithya.trustlymontyhall.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableRabbit
public class RabbitMqListenerConfiguration {

    @Value("${rabbitmq.montyhall.listener.queue}")
    private String montyHallPollQueue;

    @Value("${rabbitmq.montyhall.dlq.routing.key}")
    private String montyHallDlqRoutingKey;

    @Value("${rabbitmq.montyhall.dlq.queue}")
    private String montyHallDlq;

    @Value("${rabbitmq.montyhall.dlq.exchange}")
    private String montyHallDlqExchange;

    @Value("${rabbitmq.montyhall.listener.routingkey}")
    private String montyHallPollRoutingQueue;

    @Autowired
    private RabbitMqPublisherConfiguration rabbitMqPublisherConfiguration;

    @Bean
    public DirectExchange dlqExchange() {
        return new DirectExchange(montyHallDlqExchange);
    }

    @Bean
    public Queue getPollStatisticsQueue() {
        return new Queue(montyHallPollQueue, true, false, false, getDLQArguments());
    }

    @Bean
    public Queue getDlq() {
        return new Queue(montyHallDlq, true);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder
                .bind(getPollStatisticsQueue())
                .to(rabbitMqPublisherConfiguration.getMontyHallExchange())
                .with(montyHallPollRoutingQueue);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(getDlq())
                .to(dlqExchange())
                .with(montyHallDlqRoutingKey);
    }

    private Map<String, Object> getDLQArguments() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", montyHallDlqExchange);
        args.put("x-dead-letter-routing-key", montyHallDlqRoutingKey);
        return args;
    }
}
