package org.moonsong.booknet.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.springframework.http.MediaType;

public class UserRequester {
    static ExtractableResponse<Response> createUserRequest(UserCreateRequest userCreateRequest) {
        return RestAssured
                .given().log().all()
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(userCreateRequest)
                .when().post("/api/users")
                .then().log().all().extract();
    }
}
