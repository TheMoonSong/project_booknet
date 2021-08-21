package org.moonsong.booknet.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.moonsong.booknet.dto.LoginRequest;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.moonsong.booknet.DocumentUtils.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class UserRequester {
    static ExtractableResponse<Response> createUserRequest(UserCreateRequest userCreateRequest) {
        return RestAssured
                .given(getRequestSpecification()).log().all()
                .accept("application/json")
                .filter(document(
                        "user/create",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        )))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userCreateRequest)
                .when().post("/api/users")
                .then().log().all().extract();
    }

    static ExtractableResponse<Response> loginRequest(LoginRequest loginRequest) {
        return RestAssured
                .given(getRequestSpecification()).log().all()
                .accept("application/json")
                .filter(document(
                        "user/login-booknet",
                        getRequestPreprocessor(),
                        getResponsePreprocessor(),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
                        )))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(loginRequest)
                .when().post("/api/users/booknet/login")
                .then().log().all().extract();
    }
}
