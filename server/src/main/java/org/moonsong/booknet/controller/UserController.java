package org.moonsong.booknet.controller;

import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Void> create(@RequestBody UserCreateRequest userCreateRequest) {
        // todo 회원가입 메소드 생성
        // todo 리턴 타입 지정
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
