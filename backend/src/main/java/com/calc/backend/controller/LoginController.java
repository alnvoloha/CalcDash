package com.calc.backend.controller;

import com.calc.backend.dto.UserLoginDto;
import com.calc.backend.entity.User;
import com.calc.backend.repository.UserRepository;
import com.calc.backend.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")                // ← на время разработки
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Valid UserLoginDto dto) {

        Optional<User> opt = userRepository.findByUsername(dto.getUsername());
        if (opt.isEmpty() || !passwordEncoder.matches(dto.getPassword(), opt.get().getPassword())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Неверное имя пользователя или пароль"));
        }

        String token = jwtUtil.generateToken(opt.get().getUsername());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
