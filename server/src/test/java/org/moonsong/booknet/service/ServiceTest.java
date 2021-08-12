package org.moonsong.booknet.service;

import org.moonsong.booknet.repository.UserRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public abstract class ServiceTest {
    @MockBean
    protected UserRepository userRepository;
}
