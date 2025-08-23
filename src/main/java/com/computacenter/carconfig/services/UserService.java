package com.computacenter.carconfig.services;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.mapper.OrdersUserMapper;
import com.computacenter.carconfig.repository.OrderUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final OrderUserRepository userRepository;
    private final OrdersUserMapper ordersUserMapper;


    /**
     * Adds a single user to the database, ensuring no duplicates by email.
     *
     * @param user The User entity to be added.
     * @throws ItemAddException if a user with the same email already exists.
     */
    public void addUser(OrderUserDto user) {
        // Check if a user with the same email already exists before saving.
        Optional<OrderUser> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            String errorMessage = "User with email '" + user.getEmail() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new user: {}", user.getEmail());
        var entitytoSave = ordersUserMapper.toEntity(user);
        entitytoSave.setValid(true);
        userRepository.save(entitytoSave);
    }


    /**
     * Checks if a user with the given email exists and is valid.
     *
     * @param email The email of the user to check.
     * @return the user if the user exists and is valid, otherwise false.
     */

    public Optional<OrderUserDto> getOrderUserDtoByEmail(String email) {
        log.debug("Get the user Back if user with email {} is valid.", email);
        var userOptional = getOrderUserByMail(email);
        if(userOptional.isPresent()){
            var userDto = ordersUserMapper.toDto(userOptional.get());
            return Optional.of(userDto);
        }else {
            return Optional.empty();
        }
    }

    public Optional<OrderUser> getOrderUserByMail(String email) {
        log.debug("Get the user Back if user with email {} is valid.", email);
        return userRepository.findByEmailAndIsValid(email, true);
    }

    public static OrderUser getAnonymousUser() {
        var orderUser = new OrderUser();
        orderUser.setUserId(LocalDateTime.now().toString());
        orderUser.setUserName("annonymous");
        return orderUser;
    }
}
