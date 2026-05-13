package com.applicationdemo.carconfig.repository;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.repositories.OrderUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@DataJpaTest
@Sql("/sql/data-order-user.sql")
class OrderUserRepositoryTest {

    @Autowired
    private OrderUserRepository orderUserRepository;

    @Test
    void findByEmail_found() {
        Optional<OrderUser> result = orderUserRepository.findByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUserName());
    }

    @Test
    void findByEmail_notFound() {
        Optional<OrderUser> result = orderUserRepository.findByEmail("nouser@example.com");
        assertFalse(result.isPresent());
    }

    @Test
    void findByEmailAndIsValid_found() {
        Optional<OrderUser> result = orderUserRepository.findByEmailAndIsValid("test@example.com", true);
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUserName());
    }

    @Test
    void findByEmailAndIsValid_notFound_isNotValid() {
        Optional<OrderUser> result = orderUserRepository.findByEmailAndIsValid("inactive@example.com", true);
        assertFalse(result.isPresent());
    }

    @Test
    void findByEmailAndIsValid_notFound_nonExistent() {
        Optional<OrderUser> result = orderUserRepository.findByEmailAndIsValid("nouser@example.com", true);
        assertFalse(result.isPresent());
    }
}
