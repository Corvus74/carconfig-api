package com.applicationdemo.carconfig.entities.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarEngineOrderValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarEngineOrder carEngineOrder = new CarEngineOrder();
        carEngineOrder.setCarEngineOrderId("a-valid-engine-order-id");
        carEngineOrder.setDeleteFlag("N");

        Set<ConstraintViolation<CarEngineOrder>> violations = validator.validate(carEngineOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenCarEngineOrderIdIsTooLong_thenViolation() {
        CarEngineOrder carEngineOrder = new CarEngineOrder();
        carEngineOrder.setCarEngineOrderId("a".repeat(41));

        Set<ConstraintViolation<CarEngineOrder>> violations = validator.validate(carEngineOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("carEngineOrderId");
    }

    @Test
    void whenDeleteFlagIsTooLong_thenViolation() {
        CarEngineOrder carEngineOrder = new CarEngineOrder();
        carEngineOrder.setDeleteFlag("NN"); // 2 chars

        Set<ConstraintViolation<CarEngineOrder>> violations = validator.validate(carEngineOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("deleteFlag");
    }
}
