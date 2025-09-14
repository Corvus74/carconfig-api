package com.applicationdemo.carconfig.services;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.entities.OrderUser;
import com.applicationdemo.carconfig.exceptions.ItemAddException;
import com.applicationdemo.carconfig.mapper.OrderUserMapper;
import com.applicationdemo.carconfig.repository.OrderUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderUserServiceTest {

    @Mock
    private OrderUserRepository orderUserRepository;

    @Mock
    private OrderUserMapper orderUserMapper;

    @InjectMocks
    private OrderUserService orderUserService;

    @Test
    void addUser_success() {
        OrderUserDto userDto = new OrderUserDto("testuser", "test@example.com");
        OrderUser user = new OrderUser();

        when(orderUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(orderUserMapper.toEntity(userDto)).thenReturn(user);

        assertDoesNotThrow(() -> orderUserService.addUser(userDto));
    }

    @Test
    void addUser_alreadyExists() {
        OrderUserDto userDto = new OrderUserDto("testuser", "test@example.com");

        when(orderUserRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new OrderUser()));

        assertThrows(ItemAddException.class, () -> orderUserService.addUser(userDto));
    }

    @Test
    void getOrderUserDtoByEmail_found() {
        String email = "test@example.com";
        OrderUser user = new OrderUser();
        OrderUserDto userDto = new OrderUserDto();

        when(orderUserRepository.findByEmailAndIsValid(email, true)).thenReturn(Optional.of(user));
        when(orderUserMapper.toDto(user)).thenReturn(userDto);

        Optional<OrderUserDto> result = orderUserService.getOrderUserDtoByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
    }

    @Test
    void getOrderUserDtoByEmail_notFound() {
        String email = "test@example.com";
        when(orderUserRepository.findByEmailAndIsValid(email, true)).thenReturn(Optional.empty());

        Optional<OrderUserDto> result = orderUserService.getOrderUserDtoByEmail(email);

        assertFalse(result.isPresent());
    }

    @Test
    void getUserIfExistsIfNotCreateUnknownUser_userExists() {
        String email = "test@example.com";
        OrderUser user = new OrderUser();
        when(orderUserRepository.findByEmailAndIsValid(email, true)).thenReturn(Optional.of(user));

        OrderUser result = orderUserService.getUserIfExistsIfNotCreateUnknownUser(email);

        assertEquals(user, result);
    }

    @Test
    void getUserIfExistsIfNotCreateUnknownUser_userDoesNotExist() {
        String email = "test@example.com";
        OrderUser anonymousUser = new OrderUser();
        anonymousUser.setUserName("unknown");

        when(orderUserRepository.findByEmailAndIsValid(email, true)).thenReturn(Optional.empty());
        when(orderUserRepository.save(any(OrderUser.class))).thenReturn(anonymousUser);

        OrderUser result = orderUserService.getUserIfExistsIfNotCreateUnknownUser(email);

        assertEquals("unknown", result.getUserName());
    }

    @Test
    void getUserIfExistsIfNotCreateUnknownUser_blankEmail() {
        OrderUser anonymousUser = new OrderUser();
        anonymousUser.setUserName("unknown");

        when(orderUserRepository.save(any(OrderUser.class))).thenReturn(anonymousUser);

        OrderUser result = orderUserService.getUserIfExistsIfNotCreateUnknownUser(" ");

        assertEquals("unknown", result.getUserName());
    }
}
