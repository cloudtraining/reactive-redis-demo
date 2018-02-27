package io.cloudtraining.reactive.controller;

import io.cloudtraining.reactive.service.UserService;
import io.cloudtraining.reactive.model.CreateUser;
import io.cloudtraining.reactive.model.UpdateUser;
import io.cloudtraining.reactive.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public Mono<User> getUser(@PathVariable UUID userId) {
        return userService.getUser(userId);
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody CreateUser createUser) {
        return userService.createUser(createUser.getName());
    }

    @PutMapping("/{userId}")
    public Mono<User> updateUser(@PathVariable UUID userId, @RequestBody UpdateUser updateUser) {
        return userService.updateUser(userId, updateUser.getName());
    }

    @DeleteMapping("/{userId}")
    public Mono<Void> deleteUser(@PathVariable UUID userId) {
        return userService.deleteUser(userId);
    }

}
