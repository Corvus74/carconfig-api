package com.applicationdemo.carconfig.repositories;

import com.applicationdemo.carconfig.domain.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {
    Optional<OrderUser> findByEmail(String email);

    Optional<OrderUser> findByEmailAndIsValid(String email, boolean isValid);
}
