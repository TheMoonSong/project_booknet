package org.moonsong.booknet.service;

import org.moonsong.booknet.domain.User;
import org.moonsong.booknet.dto.LoginRequest;
import org.moonsong.booknet.dto.LoginResponse;
import org.moonsong.booknet.dto.UserCreateRequest;
import org.moonsong.booknet.exception.DuplicatedEmailException;
import org.moonsong.booknet.exception.NoSuchUserException;
import org.moonsong.booknet.exception.PasswordMissmatchException;
import org.moonsong.booknet.infrastructure.JwtUtils;
import org.moonsong.booknet.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public UserService(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    public Long createUser(UserCreateRequest userCreateRequest) {
        validateEmailDuplication(userCreateRequest.getEmail());

        User user = User.builder()
                .email(userCreateRequest.getEmail())
                .password(passwordEncoder.encode(userCreateRequest.getPassword()))
                .nickname(userCreateRequest.getNickname())
                .build();
        User saveUser = userRepository.save(user);
        return saveUser.getId();
    }

    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedEmailException();
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        User user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchUserException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new PasswordMissmatchException();
        }

        Map<String, Object> payload = JwtUtils.payloadBuilder()
                .setSubject(user.getEmail())
                .build();
        String accessToken = jwtUtils.createToken(payload);

        return new LoginResponse(accessToken);
    }
}
