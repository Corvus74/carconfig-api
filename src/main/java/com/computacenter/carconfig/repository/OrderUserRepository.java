package com.computacenter.carconfig.repository;

import com.computacenter.carconfig.entities.OrdersUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderUserRepository extends JpaRepository<OrdersUser,Integer> {
    Optional<OrdersUser> findByEmail(String email);

    Optional<OrdersUser> findByEmailAndIsValid(String email, boolean isValid);
}
