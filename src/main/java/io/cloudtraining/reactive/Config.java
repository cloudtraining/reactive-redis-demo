package io.cloudtraining.reactive;

import io.cloudtraining.reactive.model.User;
import io.cloudtraining.reactive.redis.RedisUUIDSerializer;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.util.UUID;

@SpringBootConfiguration
public class Config {

    @Bean
    public ReactiveRedisTemplate<UUID, User> userRedisTemplate(ReactiveRedisConnectionFactory factory) {
        RedisSerializationContext<UUID, User> context = RedisSerializationContext
                .<UUID, User>newSerializationContext(new StringRedisSerializer())
                .key(new RedisUUIDSerializer())
                .value(new Jackson2JsonRedisSerializer<>(User.class))
                .build();
        return new ReactiveRedisTemplate<>(factory, context);
    }

}
