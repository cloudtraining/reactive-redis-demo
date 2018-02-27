package io.cloudtraining.reactive.service;

import io.cloudtraining.reactive.UnknownUserException;
import io.cloudtraining.reactive.repository.UserRepository;
import io.cloudtraining.reactive.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<User> getUser(UUID userId) {
        return userRepository.findById(userId);
    }

    public Mono<User> createUser(String name) {
        User user = new User(UUID.randomUUID(), name);
        return userRepository.save(user);
    }

    public Mono<User> updateUser(UUID userId, String name) {
        User user = new User(userId, name);
        Mono<Boolean> userExists = userRepository.existsById(userId);
        return userExists.flatMap(exists -> {
            if (exists) {
                return userRepository.save(user);
            } else {
                return Mono.error(new UnknownUserException(userId));
            }
        });
    }

    public Mono<Void> deleteUser(UUID userId) {
        return userRepository.deleteById(userId);
    }

}
