package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.services.OrderUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
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
