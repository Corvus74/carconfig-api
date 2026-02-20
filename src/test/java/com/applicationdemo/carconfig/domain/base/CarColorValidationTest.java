package com.applicationdemo.carconfig.domain.base;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarColorValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarColor carColor = new CarColor();
        carColor.setOrderNumber("C001");
        carColor.setColorName("Valid Name");
        carColor.setDescription("Valid description");
        carColor.setProductId("P-C01");
        carColor.setColorCodeHex("#FFFFFF");

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenOrderNumberIsTooLong_thenViolation() {
        CarColor carColor = new CarColor();
        carColor.setOrderNumber("123456789012345678901"); // 21 characters

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("orderNumber");
    }

    @Test
    void whenColorNameIsTooLong_thenViolation() {
        CarColor carColor = new CarColor();
        carColor.setColorName("ThisColorNameIsWayTooLong"); // 25 characters

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("colorName");
    }

    @Test
    void whenDescriptionIsTooLong_thenViolation() {
        CarColor carColor = new CarColor();
        // Create a string longer than 400 characters
        String longDescription = "a".repeat(401);
        carColor.setDescription(longDescription);

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void whenProductIdIsTooLong_thenViolation() {
        CarColor carColor = new CarColor();
        carColor.setProductId("ThisProductIdIsDefinitelyTooLong"); // 30 characters

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("productId");
    }

    @Test
    void whenColorCodeHexIsTooLong_thenViolation() {
        CarColor carColor = new CarColor();
        carColor.setColorCodeHex("#1234567890"); // 11 characters

        Set<ConstraintViolation<CarColor>> violations = validator.validate(carColor);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("colorCodeHex");
    }
}
