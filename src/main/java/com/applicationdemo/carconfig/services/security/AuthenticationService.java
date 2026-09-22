package com.applicationdemo.carconfig.services.security;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.dto.LoginUserDto;
import com.applicationdemo.carconfig.dto.SignUpUserRequestDto;
import com.applicationdemo.carconfig.dto.SignUpUserResponseDto;
import com.applicationdemo.carconfig.repositories.OrderUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final OrderUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public SignUpUserResponseDto signup(SignUpUserRequestDto input) {
        OrderUser user = new OrderUser();
        user.setUserName(input.getUserName());
        user.setEmail(input.getEmail());
        user.setUserId(input.getUserId());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setValid(true);
        user.setValidUntil(Instant.now().atZone(ZoneId.systemDefault()).plusYears(1).toInstant());
        var storedUser =userRepository.save(user);

        return new SignUpUserResponseDto(storedUser.getEmail(), user.getUserName(), storedUser.getUserId());
    }

    public OrderUser authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
