package com.applicationdemo.carconfig.entities.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarRimOrderValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarRimOrder carRimOrder = new CarRimOrder();
        carRimOrder.setCarRimOrderId("a-valid-rim-order-id");
        carRimOrder.setDeleteFlag("N");

        Set<ConstraintViolation<CarRimOrder>> violations = validator.validate(carRimOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenCarRimOrderIdIsTooLong_thenViolation() {
        CarRimOrder carRimOrder = new CarRimOrder();
        carRimOrder.setCarRimOrderId("a".repeat(41));

        Set<ConstraintViolation<CarRimOrder>> violations = validator.validate(carRimOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("carRimOrderId");
    }

    @Test
    void whenDeleteFlagIsTooLong_thenViolation() {
        CarRimOrder carRimOrder = new CarRimOrder();
        carRimOrder.setDeleteFlag("NN"); // 2 chars

        Set<ConstraintViolation<CarRimOrder>> violations = validator.validate(carRimOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("deleteFlag");
    }
}
