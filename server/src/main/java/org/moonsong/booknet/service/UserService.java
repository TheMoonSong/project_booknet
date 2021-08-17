package org.moonsong.booknet.service;

import org.moonsong.booknet.domain.User;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.exception.DuplicatedEmailException;
import org.moonsong.booknet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Long createUser(UserCreateRequest userCreateRequest) {
        validateEmailDuplication(userCreateRequest.getEmail());

        User user = User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .nickname(userCreateRequest.getNickName())
                .build();
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }
}
