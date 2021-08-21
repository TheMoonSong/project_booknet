package org.moonsong.booknet.controller;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonsong.booknet.dto.LoginRequest;
import org.moonsong.booknet.dto.LoginResponse;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.moonsong.booknet.Fixture.*;
import static org.moonsong.booknet.controller.UserRequester.*;

class UserControllerTest extends AcceptanceTest {
    @Test
    @DisplayName("정상적인 회원가입 요청이 들어오면 Created로 응답하고, Location 헤더에 위치를 담아 보낸다.")
    void register() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest(EMAIL, PASSWORD, NICKNAME);

        // when
        ExtractableResponse<Response> userCreateResponse = createUserRequest(userCreateRequest);

        // then
        assertEquals(userCreateResponse.statusCode(), HttpStatus.CREATED.value());

        String locationRegex = "(/users/[0-9])?";
        Pattern locationPattern = Pattern.compile(locationRegex);
        Matcher matcher = locationPattern.matcher(userCreateResponse.header("location"));
        assertTrue(matcher.find());
    }

    @Test
    @DisplayName("올바른 이메일과 비밀번호로부터 로그인할 수 있으며, jwt를 발급한다.")
    void login() {
        // given
        UserCreateRequest userCreateRequest = new UserCreateRequest(EMAIL, PASSWORD, NICKNAME);
        createUserRequest(userCreateRequest);

        // when, then
        ExtractableResponse<Response> loginResponse = loginRequest(new LoginRequest(EMAIL, PASSWORD));
        assertThat(loginResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        // when, then
        String accessToken = loginResponse.as(LoginResponse.class).getAccessToken();
        assertThat(accessToken).isNotBlank();
    }
}
