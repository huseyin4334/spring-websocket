package org.example.springwebsocket.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.example.springwebsocket.utils.KafkaConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    NewTopic paymentTopic() {
        return new NewTopic(KafkaConstants.PAYMENT_TOPIC, 1, (short) 1);
    }
}
