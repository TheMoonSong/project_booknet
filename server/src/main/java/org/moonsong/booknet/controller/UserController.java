package org.moonsong.booknet.controller;

import org.moonsong.booknet.dto.LoginRequest;
import org.moonsong.booknet.dto.LoginResponse;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestBody UserCreateRequest userCreateRequest) {
        Long userId = userService.createUser(userCreateRequest);
        return ResponseEntity.created(URI.create("/users/" + userId)).build();
    }

    @PostMapping("/booknet/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
