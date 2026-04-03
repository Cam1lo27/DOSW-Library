package edu.eci.dosw.tdd.core.service;

import edu.eci.dosw.tdd.core.security.JwtTokenProvider;
import edu.eci.dosw.tdd.persistence.dao.UserEntity;
import edu.eci.dosw.tdd.persistence.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas."));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Credenciales inválidas.");
        }

        return jwtTokenProvider.generateToken(user.getId(), user.getRole());
    }
}