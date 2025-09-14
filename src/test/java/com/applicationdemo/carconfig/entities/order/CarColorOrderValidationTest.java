package com.applicationdemo.carconfig.entities.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarColorOrderValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarColorOrder carColorOrder = new CarColorOrder();
        carColorOrder.setCarColorOrderId("a-valid-color-order-id");
        carColorOrder.setDeleteFlag("N");

        Set<ConstraintViolation<CarColorOrder>> violations = validator.validate(carColorOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenCarColorOrderIdIsTooLong_thenViolation() {
        CarColorOrder carColorOrder = new CarColorOrder();
        carColorOrder.setCarColorOrderId("a".repeat(41));

        Set<ConstraintViolation<CarColorOrder>> violations = validator.validate(carColorOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("carColorOrderId");
    }

    @Test
    void whenDeleteFlagIsTooLong_thenViolation() {
        CarColorOrder carColorOrder = new CarColorOrder();
        carColorOrder.setDeleteFlag("NN"); // 2 chars

        Set<ConstraintViolation<CarColorOrder>> violations = validator.validate(carColorOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("deleteFlag");
    }
}
