package org.moonsong.booknet.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.moonsong.booknet.Fixture.EMAIL;

@SpringBootTest
@ActiveProfiles("test")
class JwtUtilsTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    @DisplayName("토큰을 생성한다.")
    void createToken() {
        // given
        Map<String, Object> payload = JwtUtils.payloadBuilder()
                .setSubject(EMAIL)
                .build();

        // when
        String token = jwtUtils.createToken(payload);

        // then
        assertThat(token).isInstanceOf(String.class);
    }


    @Test
    @DisplayName("토큰을 검증한다.")
    void validateToken() {
        // given
        Map<String, Object> payload = JwtUtils.payloadBuilder()
                .setSubject(EMAIL)
                .build();

        String token = jwtUtils.createToken(payload);

        // when, then
        jwtUtils.validateToken(token);
    }
}
