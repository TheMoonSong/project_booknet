package org.moonsong.booknet.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.moonsong.booknet.domain.User;
import org.moonsong.booknet.exception.NoSuchUserException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.moonsong.booknet.Fixture.*;

class UserRepositoryTest extends RepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("특정 email을 가진 유저가 존재하는지 확인할 수 있다.")
    void exitsByEmail() {
        // given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();
        User saveUser = userRepository.save(user);

        // when
        boolean actual = userRepository.existsByEmail(saveUser.getEmail());

        // then
        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("특정 Email을 가진 유저를 찾아온다.")
    void findByEmail() {
        // given
        User user = User.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .nickname(NICKNAME)
                .build();
        User saveUser = userRepository.save(user);

        // when
        User findUser = userRepository.findByEmail(saveUser.getEmail())
                .orElseThrow(NoSuchUserException::new);

        // then
        assertThat(findUser).isEqualTo(saveUser);
    }
}
