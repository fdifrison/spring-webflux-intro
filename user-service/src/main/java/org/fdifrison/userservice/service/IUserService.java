package org.fdifrison.userservice.service;

import org.fdifrison.userservice.dto.UserDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IUserService {

    Flux<UserDto> getUsers();

    Mono<UserDto> getUserById(Integer id);

    Mono<Boolean> updateUser(Integer id, UserDto user);

    Mono<Boolean> deleteUser(Integer id);

    Mono<Boolean> createUser(Mono<UserDto> user);



}
