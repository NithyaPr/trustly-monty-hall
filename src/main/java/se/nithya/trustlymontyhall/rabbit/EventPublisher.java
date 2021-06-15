package se.nithya.trustlymontyhall.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.nithya.trustlymontyhall.dto.GameStat;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.montyhall.topic.exchange}")
    private String exchangeName;

    @Value("${rabbitmq.montyhall.publisher.routingkey}")
    private String routingKey;

    @Value("${rabbitmq.montyhall.publisher.attributetype}")
    private String montyHallAttributeType;

    @Autowired
    public EventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(GameStat statistics) {
        try {
            String body = objectMapper.writeValueAsString(statistics);
            rabbitTemplate.send(exchangeName, routingKey, MessageBuilder
                    .withBody(body.getBytes(StandardCharsets.UTF_8)).andProperties(getHeaderProps()).build());

        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }

    private MessageProperties getHeaderProps() {
        String transactionId = UUID.randomUUID().toString();
        return MessagePropertiesBuilder.newInstance()
                .setHeader("TRANSACTION_ID",
                        transactionId)
                .setType(montyHallAttributeType)
                .setTimestamp(getZoneDateTimeFromLocal())
                .setContentType("application/json")
                .setMessageId(transactionId)
                .build();
    }

    private static Date getZoneDateTimeFromLocal() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zoneId = ZoneId.of("Europe/Stockholm");
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }
}
