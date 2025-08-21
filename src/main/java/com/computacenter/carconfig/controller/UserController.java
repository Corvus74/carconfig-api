package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.mapper.OrdersUserMapper;
import com.computacenter.carconfig.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user/")
public class UserController {
    private final UserService userService;
    private final OrdersUserMapper userMapper;

    /**
     * Retrieves a user by their ID.
     * @param email The email of the user to retrieve.
     * @return The UserDto of the requested user.
     */
    @GetMapping(path = "/get/{email}", produces = "application/json")
    public OrderUserDto getUser(@PathVariable String email) {
        log.info("Attempting to retrieve user with email: {}", email);
        return userService.getUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + email));
    }

    /**
     * Creates or updates a user.
     * @param userDto The UserDto containing the user's data.
     * @return A ResponseDto indicating the result of the operation.
     */
    @PostMapping(path = "/add", produces = "application/json", consumes = "application/json")
    public ResponseDto setUser(@RequestBody OrderUserDto userDto) {
        log.info("Attempting to save user with email: {}", userDto.getEmail());
        try {
            userService.addUser(userMapper.toEntity(userDto));
            return new ResponseDto("User saved successfully", TransferStatus.SUCCESS);
        } catch (Exception e) {
            log.error("Failed to save user: {}", e.getMessage());
            return new ResponseDto("Failed to save user", TransferStatus.FAILURE, e.getMessage());
        }
    }
}
