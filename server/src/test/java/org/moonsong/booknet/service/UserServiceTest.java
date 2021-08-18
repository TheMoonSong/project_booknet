package org.moonsong.booknet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonsong.booknet.domain.User;
import org.moonsong.booknet.dto.LoginRequest;
import org.moonsong.booknet.dto.LoginResponse;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.exception.DuplicatedEmailException;
import org.moonsong.booknet.exception.NoSuchUserException;
import org.moonsong.booknet.exception.PasswordMissmatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.moonsong.booknet.Fixture.*;

class UserServiceTest extends ServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원을 생성한다.")
    void create() {
        // given
        given(userRepository.existsByEmail(EMAIL))
                .willReturn(false);

        String encryptedPassword = passwordEncoder.encode(PASSWORD);
        given(userRepository.save(any(User.class)))
                .willReturn(new User(new Random().nextLong(), EMAIL, encryptedPassword, NICKNAME));

        // when
        UserCreateRequest userCreateRequest = new UserCreateRequest(EMAIL, PASSWORD, NICKNAME);
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

    @Test
    @DisplayName("유효한 정보를 통해 토큰을 얻어올 수 있다.")
    void login() {
        // given
        String encryptedPassword = passwordEncoder.encode(PASSWORD);
        given(userRepository.findByEmail(EMAIL))
                .willReturn(Optional.of(new User(new Random().nextLong(), EMAIL, encryptedPassword, NICKNAME)));

        // when
        LoginResponse loginResponse = userService.login(new LoginRequest(EMAIL, PASSWORD));

        // then
        assertThat(loginResponse.getAccessToken()).isNotBlank();
    }

    @Test
    @DisplayName("존재하지 않는 회원의 이메일로 로그인할 경우 예외가 발생한다.")
    void loginFailByInvalidEmail() {
        // given
        given(userRepository.findByEmail(any(String.class)))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> userService.login(new LoginRequest("wrong email", PASSWORD)))
                .isInstanceOf(NoSuchUserException.class);
    }

    @Test
    @DisplayName("유효하지 않은 비밀번호로 로그인할 경우 예외가 발생한다.")
    void loginFailByInvalidPassword() {
        // given
        given(userRepository.findByEmail(any(String.class)))
                .willReturn(Optional.of(new User(new Random().nextLong(), EMAIL, PASSWORD, NICKNAME)));

        // when, then
        assertThatThrownBy(() -> userService.login(new LoginRequest(EMAIL, "wrong password")))
                .isInstanceOf(PasswordMissmatchException.class);
    }
}
