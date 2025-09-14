package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarOrderStatusDto;
import com.applicationdemo.carconfig.entities.order.OrderStatus;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class OrderStatusMapperTest {

    @Autowired
    private OrderStatusMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarOrderStatusDto dto = new CarOrderStatusDto();
        dto.setCurrentStatus(OrderStatusEnum.PENDING);
        dto.setShippingDate(LocalDate.of(2024, 1, 1));

        // Act
        OrderStatus entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCurrentStatus()).isEqualTo(OrderStatusEnum.PENDING);
        assertThat(entity.getShippingDate()).isEqualTo(LocalDate.of(2024, 1, 1));
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        OrderStatus entity = new OrderStatus();
        entity.setCurrentStatus(OrderStatusEnum.IN_PROGRESS);
        entity.setDeliveryDate(LocalDate.of(2024, 2, 1));

        // Act
        CarOrderStatusDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCurrentStatus()).isEqualTo(OrderStatusEnum.IN_PROGRESS);
        assertThat(dto.getDeliveryDate()).isEqualTo(LocalDate.of(2024, 2, 1));
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        OrderStatus entity = new OrderStatus();
        entity.setCurrentStatus(OrderStatusEnum.PENDING);
        entity.setShippingDate(LocalDate.of(2024, 1, 1));

        CarOrderStatusDto patchDto = new CarOrderStatusDto();
        patchDto.setCurrentStatus(OrderStatusEnum.IN_PROGRESS);
        patchDto.setShippingDate(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getCurrentStatus()).isEqualTo(OrderStatusEnum.IN_PROGRESS);
        assertThat(entity.getShippingDate()).isEqualTo(LocalDate.of(2024, 1, 1)); // Unchanged
    }

    @Test
    void toDto_returnsNull_whenEntityIsNull() {
        assertThat(mapper.toDto(null)).isNull();
    }

    @Test
    void toEntity_returnsNull_whenDtoIsNull() {
        assertThat(mapper.toEntity(null)).isNull();
    }
}
