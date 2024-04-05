package org.fdifrison.userservice.controller;

import org.fdifrison.userservice.dto.ResponseDto;
import org.fdifrison.userservice.dto.UserDto;
import org.fdifrison.userservice.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("user")
public class UserController {

    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("all")
    public Flux<ResponseEntity<UserDto>> getAllUsers() {
        return service.getUsers().map(user -> ResponseEntity.ok().body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable Integer id) {
        return service.getUserById(id).map(user -> ResponseEntity.ok().body(user))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public Mono<ResponseEntity<ResponseDto>> createUser(@RequestBody Mono<UserDto> userDto) {
        return service.createUser(userDto).map(r -> {
            if (r) {
                return ResponseEntity.ok().body(new ResponseDto(CREATED.toString(), CREATED.getReasonPhrase()));
            } else {
                return ResponseEntity.badRequest()
                        .body(new ResponseDto(BAD_REQUEST.toString(), BAD_REQUEST.getReasonPhrase()));
            }
        });
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ResponseDto>> updateUser(@RequestBody Mono<UserDto> userDto, @PathVariable Integer id) {
        return userDto.flatMap(user -> service.updateUser(id, user).map(r -> {
                    if (r) {
                        return ResponseEntity.ok().body(new ResponseDto(OK.toString(), OK.getReasonPhrase()));
                    } else {
                        return ResponseEntity.badRequest()
                                .body(new ResponseDto(NOT_FOUND.toString(), NOT_FOUND.getReasonPhrase()));
                    }
                })
        );
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<ResponseDto>> deleteUser(@PathVariable Integer id) {
        return service.deleteUser(id).map(deleted -> deleted ?
                ResponseEntity.ok().body(new ResponseDto(OK.toString(), OK.getReasonPhrase())) :
                ResponseEntity.badRequest().body(new ResponseDto(NOT_FOUND.toString(), NOT_FOUND.getReasonPhrase())));
    }


}
