package org.moonsong.booknet.controller;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.moonsong.booknet.DocumentUtils.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
@ActiveProfiles("test")
public class AcceptanceTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void assignRandomPort() {
        RestAssured.port = port;
    }

    @BeforeEach
    public void setUpDocs(RestDocumentationContextProvider restDocumentation) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
        setRequestSpecification(requestSpecification);
    }
}

