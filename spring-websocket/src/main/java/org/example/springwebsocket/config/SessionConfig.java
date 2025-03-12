package org.example.springwebsocket.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.Objects;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 57600)
@RequiredArgsConstructor
@Configuration
public class SessionConfig {

    private final Environment environment;

    @SpringSessionRedisConnectionFactory
    @Bean
    LettuceConnectionFactory sessionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(Objects.requireNonNull(environment.getProperty("spring.data.redis.host")));
        redisStandaloneConfiguration.setPort(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.data.redis.port"))));
        redisStandaloneConfiguration.setDatabase(1);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> sessionRedisTemplate(
            @Qualifier("sessionFactory") LettuceConnectionFactory sessionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(sessionFactory);
        return redisTemplate;
    }
}
