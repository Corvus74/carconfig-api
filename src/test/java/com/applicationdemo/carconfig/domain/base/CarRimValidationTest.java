package com.applicationdemo.carconfig.domain.base;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarRimValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarRim carRim = new CarRim();
        carRim.setOrderNumber("R001");
        carRim.setRimName("Valid Name");
        carRim.setModel("S18A");
        carRim.setDescription("Valid description");
        carRim.setProductId("P-R01");

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenOrderNumberIsTooLong_thenViolation() {
        CarRim carRim = new CarRim();
        carRim.setOrderNumber("123456789012345678901"); // 21 chars

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("orderNumber");
    }

    @Test
    void whenRimNameIsTooLong_thenViolation() {
        CarRim carRim = new CarRim();
        carRim.setRimName("ThisRimNameIsDefinitelyTooLong"); // 29 chars

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("rimName");
    }

    @Test
    void whenModelIsTooLong_thenViolation() {
        CarRim carRim = new CarRim();
        carRim.setModel("ThisModelNameIsWayTooLong"); // 25 chars

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("model");
    }

    @Test
    void whenDescriptionIsTooLong_thenViolation() {
        CarRim carRim = new CarRim();
        carRim.setDescription("a".repeat(401));

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void whenProductIdIsTooLong_thenViolation() {
        CarRim carRim = new CarRim();
        carRim.setProductId("ThisProductIdIsDefinitelyTooLong"); // 30 chars

        Set<ConstraintViolation<CarRim>> violations = validator.validate(carRim);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("productId");
    }
}
