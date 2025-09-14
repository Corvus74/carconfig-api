package com.applicationdemo.carconfig.entities.base;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialEquipmentValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        SpecialEquipment specialEquipment = new SpecialEquipment();
        specialEquipment.setOrderNumber("SE001");
        specialEquipment.setEquipmentName("Valid Name");
        specialEquipment.setDescription("Valid description");
        specialEquipment.setProductId("P-SE01");

        Set<ConstraintViolation<SpecialEquipment>> violations = validator.validate(specialEquipment);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenOrderNumberIsTooLong_thenViolation() {
        SpecialEquipment specialEquipment = new SpecialEquipment();
        specialEquipment.setOrderNumber("123456789012345678901"); // 21 chars

        Set<ConstraintViolation<SpecialEquipment>> violations = validator.validate(specialEquipment);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("orderNumber");
    }

    @Test
    void whenEquipmentNameIsTooLong_thenViolation() {
        SpecialEquipment specialEquipment = new SpecialEquipment();
        specialEquipment.setEquipmentName("This Equipment Name Is Definitely Way Too Long"); // 45 chars

        Set<ConstraintViolation<SpecialEquipment>> violations = validator.validate(specialEquipment);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("equipmentName");
    }

    @Test
    void whenDescriptionIsTooLong_thenViolation() {
        SpecialEquipment specialEquipment = new SpecialEquipment();
        specialEquipment.setDescription("a".repeat(401));

        Set<ConstraintViolation<SpecialEquipment>> violations = validator.validate(specialEquipment);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("description");
    }

    @Test
    void whenProductIdIsTooLong_thenViolation() {
        SpecialEquipment specialEquipment = new SpecialEquipment();
        specialEquipment.setProductId("ThisProductIdIsDefinitelyTooLong"); // 30 chars

        Set<ConstraintViolation<SpecialEquipment>> violations = validator.validate(specialEquipment);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("productId");
    }
}
