package org.example.springwebsocket.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.springwebsocket.utils.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class KafkaTopicConfig {

    private final Environment environment;

    @Bean
    NewTopic paymentTopic() {
        // retention.ms will be 1 day for this topic.
        // replication factor means that there will be 3 copies of each message in the topic.
        // num Partitions means that there will be 3 partitions in the topic.
        // The difference between the replication factor and the number of partitions is that the replication factor is the number of copies of each partition in the topic.
        return new NewTopic(KafkaConstants.PAYMENT_TOPIC, 3, (short) 3)
                .configs(Map.of(
                        KafkaConstants.RETENTION_MS, environment.getProperty("payment.response.consumer.retention.ms")
                ));
    }
}
