package com.applicationdemo.carconfig.controller;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.dto.ResponseDto;
import com.applicationdemo.carconfig.enums.TransferStatus;
import com.applicationdemo.carconfig.services.security.OrderUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class OrderUserController {
    private final OrderUserService orderUserService;


    /**
     * Retrieves a user by their ID.
     * @param email The email of the user to retrieve.
     * @return The UserDto of the requested user.
     */
    @GetMapping(path = "/get/{email}", produces = "application/json")
    public OrderUserDto getUser(@PathVariable String email) {
        log.info("Attempting to retrieve user with email: {}", email);
        return orderUserService.getOrderUserDtoByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + email));
    }

    /**
     * Creates or updates a user.
     * @param orderUserDto The UserDto containing the user's data.
     * @return A ResponseDto indicating the result of the operation.
     */
    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseDto setUser(@RequestBody OrderUserDto orderUserDto) {
        log.info("Attempting to save user with email: {}", orderUserDto.getEmail());
        try {
            orderUserService.addUser(orderUserDto);
            return new ResponseDto("User saved successfully", TransferStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Failed to save user: {}", e.getMessage());
            return new ResponseDto("Failed to save user", TransferStatus.FAILURE, e.getMessage());
        }
    }
}
