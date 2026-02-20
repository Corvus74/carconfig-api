package com.applicationdemo.carconfig.domain.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStatusValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setDeleteFlag("N");

        Set<ConstraintViolation<OrderStatus>> violations = validator.validate(orderStatus);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenDeleteFlagIsTooLong_thenViolation() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setDeleteFlag("NN"); // 2 chars

        Set<ConstraintViolation<OrderStatus>> violations = validator.validate(orderStatus);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("deleteFlag");
    }
}
