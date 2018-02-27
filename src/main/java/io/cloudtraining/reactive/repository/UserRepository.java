package io.cloudtraining.reactive.repository;

import io.cloudtraining.reactive.UnknownUserException;
import io.cloudtraining.reactive.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

// As of Spring Boot 2.0.0.RC2, Spring Data Redis does not support ReactiveCrudRepository
@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final ReactiveRedisTemplate<UUID, User> redis;

    public Mono<User> findById(UUID userId) {
        return redis.opsForValue()
                .get(userId);
    }

    public Mono<User> save(User user) {
        return redis.opsForValue()
                .set(user.getId(), user)
                .then(redis.persist(user.getId()))
                .thenReturn(user);
    }

    public Mono<Boolean> existsById(UUID userId) {
        return redis.hasKey(userId);
    }

    public Mono<Void> deleteById(UUID userId) {
        return redis.delete(userId)
                .filter(count -> count > 0)
                .switchIfEmpty(Mono.error(new UnknownUserException(userId)))
                .then();
    }

}
