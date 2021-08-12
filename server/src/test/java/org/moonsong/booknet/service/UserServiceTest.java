package org.moonsong.booknet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonsong.booknet.domain.User;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.exception.DuplicatedEmailException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.moonsong.booknet.Fixture.*;

class UserServiceTest extends ServiceTest {
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원을 생성한다.")
    void create() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest(EMAIL, PASSWORD, NICKNAME);
        given(userRepository.existsByEmail(EMAIL))
                .willReturn(false);
        given(userRepository.save(any(User.class)))
                .willReturn(new User(new Random().nextLong(), EMAIL, PASSWORD, NICKNAME));

        // when
        Long createUserId = userService.createUser(userCreateRequest);

        // then
        assertThat(createUserId).isInstanceOf(Long.class);
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입을 요청할 경우 예외가 발생한다.")
    void createUserByDuplicatedEmail() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest(EMAIL, PASSWORD, NICKNAME);
        given(userRepository.existsByEmail(EMAIL))
                .willReturn(true);

        // when
        assertThatThrownBy(() -> userService.createUser(userCreateRequest))
                .isInstanceOf(DuplicatedEmailException.class);
    }
}
