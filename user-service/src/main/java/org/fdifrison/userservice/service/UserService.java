package org.fdifrison.userservice.service;

import org.fdifrison.userservice.dto.UserDto;
import org.fdifrison.userservice.repository.UserRepository;
import org.fdifrison.userservice.util.UserMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
class UserService implements IUserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<UserDto> getUsers() {
        return this.repository.findAll().map(UserMapper::toDto);
    }

    @Override
    public Mono<Boolean> createUser(Mono<UserDto> user) {
        return user.map(UserMapper::fromDto)
                .flatMap(this.repository::save)
                .map(Objects::nonNull);
    }

    @Override
    public Mono<UserDto> getUserById(Integer id) {
        return repository.findById(id).map(UserMapper::toDto);
    }

    @Override
    public Mono<Boolean> updateUser(Integer id, UserDto user) {
        return repository.findById(id)
                .flatMap(updatedUser -> {
                    if (updatedUser != null) {
                        updatedUser.setName(user.name());
                        updatedUser.setBalance(user.balance());
                        return this.repository.save(updatedUser).map(u -> true);
                    } else
                        return Mono.just(false);
                });
    }

    @Override
    public Mono<Boolean> deleteUser(Integer id) {
        return repository.findById(id)
                .flatMap(updatedUser -> {
                    if (updatedUser != null) {
                        return this.repository.delete(updatedUser).then(Mono.just(true));
                    } else
                        return Mono.just(false);
                });
    }
}
