package com.applicationdemo.carconfig.domain.base;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarEngineValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        CarEngine carEngine = new CarEngine();
        carEngine.setOrderNumber("E001");
        carEngine.setDescription("Valid description");
        carEngine.setProductId("P-E01");
        carEngine.setModel("B48");
        carEngine.setCarName("330i");
        carEngine.setDrivetrain("RWD");

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenOrderNumberIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setOrderNumber("123456789012345678901"); // 21 chars

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("orderNumber");
    }

    @Test
    void whenDescriptionIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setDescription("a".repeat(401));

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void whenProductIdIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setProductId("123456789012345678901"); // 21 chars

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("productId");
    }

    @Test
    void whenModelIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setModel("123456789012345678901"); // 21 chars

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("model");
    }

    @Test
    void whenCarNameIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setCarName("a".repeat(256));

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("carName");
    }

    @Test
    void whenDrivetrainIsTooLong_thenViolation() {
        CarEngine carEngine = new CarEngine();
        carEngine.setDrivetrain("a".repeat(51));

        Set<ConstraintViolation<CarEngine>> violations = validator.validate(carEngine);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("drivetrain");
    }
}
