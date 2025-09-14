package com.applicationdemo.carconfig.entities.order;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SpecialEquipmentOrderValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        SpecialEquipmentOrder specialEquipmentOrder = new SpecialEquipmentOrder();
        specialEquipmentOrder.setSpecialEquipmentOrderId("a-valid-se-order-id");
        specialEquipmentOrder.setDeleteFlag("N");

        Set<ConstraintViolation<SpecialEquipmentOrder>> violations = validator.validate(specialEquipmentOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenSpecialEquipmentOrderIdIsTooLong_thenViolation() {
        SpecialEquipmentOrder specialEquipmentOrder = new SpecialEquipmentOrder();
        specialEquipmentOrder.setSpecialEquipmentOrderId("a".repeat(41));

        Set<ConstraintViolation<SpecialEquipmentOrder>> violations = validator.validate(specialEquipmentOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("specialEquipmentOrderId");
    }

    @Test
    void whenDeleteFlagIsTooLong_thenViolation() {
        SpecialEquipmentOrder specialEquipmentOrder = new SpecialEquipmentOrder();
        specialEquipmentOrder.setDeleteFlag("NN"); // 2 chars

        Set<ConstraintViolation<SpecialEquipmentOrder>> violations = validator.validate(specialEquipmentOrder);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("deleteFlag");
    }
}
