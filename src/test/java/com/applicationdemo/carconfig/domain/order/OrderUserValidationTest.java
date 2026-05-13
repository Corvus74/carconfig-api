package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OrderUserValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        OrderUser orderUser = new OrderUser();
        orderUser.setUserId("a-valid-user-id");
        orderUser.setUserName("validuser");
        orderUser.setEmail("valid@email.com");

        Set<ConstraintViolation<OrderUser>> violations = validator.validate(orderUser);

        assertThat(violations).isEmpty();
    }

    @Test
    void whenUserIdIsTooLong_thenViolation() {
        OrderUser orderUser = new OrderUser();
        orderUser.setUserId("a".repeat(41));

        Set<ConstraintViolation<OrderUser>> violations = validator.validate(orderUser);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("userId");
    }

    @Test
    void whenUserNameIsTooLong_thenViolation() {
        OrderUser orderUser = new OrderUser();
        orderUser.setUserName("thisusernameistoolong"); // 21 chars

        Set<ConstraintViolation<OrderUser>> violations = validator.validate(orderUser);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("userName");
    }

    @Test
    void whenEmailIsTooLong_thenViolation() {
        OrderUser orderUser = new OrderUser();
        orderUser.setEmail("thisemailistoolong@example.com"); // 31 chars

        Set<ConstraintViolation<OrderUser>> violations = validator.validate(orderUser);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getPropertyPath().toString()).isEqualTo("email");
    }
}
