package com.applicationdemo.carconfig.domain.order;

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

}
