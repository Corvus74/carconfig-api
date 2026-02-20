package com.applicationdemo.carconfig.domain.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarOrderValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarOrder carOrder = new CarOrder();
        carOrder.setCarOrderId("a-valid-order-id");
        carOrder.setDescription("A valid description.");

        Set<ConstraintViolation<CarOrder>> violations = validator.validate(carOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenCarOrderIdIsTooLong_thenViolation() {
        CarOrder carOrder = new CarOrder();
        carOrder.setCarOrderId("a".repeat(41));

        Set<ConstraintViolation<CarOrder>> violations = validator.validate(carOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("carOrderId");
    }

    @Test
    void whenDescriptionIsTooLong_thenViolation() {
        CarOrder carOrder = new CarOrder();
        carOrder.setDescription("a".repeat(401));

        Set<ConstraintViolation<CarOrder>> violations = validator.validate(carOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }
}
