package com.computacenter.carconfig.services;

import com.computacenter.carconfig.entities.OrdersUser;
import com.computacenter.carconfig.exceptions.ItemAddException;
import com.computacenter.carconfig.repository.OrderUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final OrderUserRepository userRepository;


    /**
     * Adds a single user to the database, ensuring no duplicates by email.
     *
     * @param user The User entity to be added.
     * @throws ItemAddException if a user with the same email already exists.
     */
    public void addUser(OrdersUser user) {
        // Check if a user with the same email already exists before saving.
        Optional<OrdersUser> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            String errorMessage = "User with email '" + user.getEmail() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }

        log.info("Adding new user: {}", user.getEmail());
        user.setValid(true);
        userRepository.save(user);
    }


    /**
     * Checks if a user with the given email exists and is valid.
     *
     * @param email The email of the user to check.
     * @return the user if the user exists and is valid, otherwise false.
     */

    public Optional<OrdersUser> getUserByEmail(String email) {
        log.debug("Get the user Back if user with email {} is valid.", email);
        return userRepository.findByEmailAndIsValid(email, true);
    }



}
