package org.moonsong.booknet.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.springframework.http.MediaType;

import static org.moonsong.booknet.DocumentUtils.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

public class UserRequester {
    static ExtractableResponse<Response> createUserRequest(UserCreateRequest userCreateRequest) {
        return RestAssured
                .given(getRequestSpecification()).log().all()
                .accept("application/json")
                .filter(document("member/create", getRequestPreprocessor(), getResponsePreprocessor()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userCreateRequest)
                .when().post("/api/users")
                .then().log().all().extract();
    }
}
