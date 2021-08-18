package org.moonsong.booknet.infrastructure;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.moonsong.booknet.Fixture.EMAIL;

class JwtUtilsTest {
    // todo 설정 파일에 의해 필요 값을 주입받아 빈으로 생성하고, Autowired하기
    private JwtUtils jwtUtils = new JwtUtils();

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
