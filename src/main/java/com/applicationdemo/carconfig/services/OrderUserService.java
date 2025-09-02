package com.applicationdemo.carconfig.services;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.entities.OrderUser;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.mapper.OrderUserMapper;
import com.applicationdemo.carconfig.repository.OrderUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderUserService {
    private final OrderUserRepository orderUserRepository;
    private final OrderUserMapper orderUserMapper;


    /**
     * Adds a single user to the database, ensuring no duplicates by email.
     *
     * @param user The User entity to be added.
     * @throws ItemAddException if a user with the same email already exists.
     */
    public void addUser(OrderUserDto user) {
        // Check if a user with the same email already exists before saving.
        Optional<OrderUser> existingUser = orderUserRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            String errorMessage = "User with email '" + user.getEmail() + "' already exists.";
            log.warn(errorMessage);
            throw new ItemAddException(errorMessage);
        }
        log.info("Adding new user: {}", user.getEmail());
        var entityToSave = orderUserMapper.toEntity(user);
        entityToSave.setValid(true);
        orderUserRepository.save(entityToSave);
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
            var userDto = orderUserMapper.toDto(userOptional.get());
            return Optional.of(userDto);
        }else {
            return Optional.empty();
        }
    }

    public Optional<OrderUser> getOrderUserByMail(String email) {
        return orderUserRepository.findByEmailAndIsValid(email, true);
    }

    private OrderUser createAnonymousUser() {
        var orderUser = new OrderUser();
        orderUser.setUserId(LocalDateTime.now().toString());
        orderUser.setUserName("unknown");
        orderUser.setValid(true);
        return orderUserRepository.save(orderUser);
    }


    public OrderUser getUserIfExistsIfNotCreateUnknownUser(String userMail) {
        if (StringUtils.isBlank(userMail)) {
            return createAnonymousUser();
        }
        var orderUser = getOrderUserByMail(userMail);
        return orderUser.orElseGet(this::createAnonymousUser);
    }
}
