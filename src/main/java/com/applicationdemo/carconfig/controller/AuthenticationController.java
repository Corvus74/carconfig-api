package com.applicationdemo.carconfig.controller;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.dto.LoginResponse;
import com.applicationdemo.carconfig.dto.LoginUserDto;
import com.applicationdemo.carconfig.dto.SignUpUserRequestDto;
import com.applicationdemo.carconfig.dto.SignUpUserResponseDto;
import com.applicationdemo.carconfig.security.JwtService;
import com.applicationdemo.carconfig.security.OrderUserDetails;
import com.applicationdemo.carconfig.services.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/signup", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SignUpUserResponseDto> signup(@RequestBody SignUpUserRequestDto signUpUserRequest) {
        SignUpUserResponseDto registeredUser = authenticationService.signup(signUpUserRequest);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(path="/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        try {
            OrderUser authenticatedUser = authenticationService.authenticate(loginUserDto);

            String jwtToken = jwtService.generateToken(new OrderUserDetails(authenticatedUser));

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(jwtToken)
                    .expiresIn(jwtService.getExpirationTime())
                    .build();

            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException _) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        } catch (Exception _) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authentication failed");
        }
    }
}
