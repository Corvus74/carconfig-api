package com.computacenter.carconfig.repository;

import com.computacenter.carconfig.entities.OrderUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderUserRepository extends JpaRepository<OrderUser, Long> {
    Optional<OrderUser> findByEmail(String email);

    Optional<OrderUser> findByEmailAndIsValid(String email, boolean isValid);
}
