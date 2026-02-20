package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarRimOrderDto;
import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.domain.base.CarRim;
import com.applicationdemo.carconfig.domain.order.CarRimOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarRimOrderMapperTest {

    @Autowired
    private CarRimOrderMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarRimOrderDto dto = new CarRimOrderDto();
        dto.setCarRimOrderId("cro-1");
        CarRimDto rimDto = new CarRimDto();
        rimDto.setRimName("Sport");
        dto.setCarRim(rimDto);

        // Act
        CarRimOrder entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getCarRimOrderId()).isEqualTo("cro-1");
        assertThat(entity.getCarRim()).isNotNull();
        assertThat(entity.getCarRim().getRimName()).isEqualTo("Sport");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarRimOrder entity = new CarRimOrder();
        entity.setCarRimOrderId("cro-2");
        CarRim rim = new CarRim();
        rim.setRimName("Classic");
        entity.setCarRim(rim);

        // Act
        CarRimOrderDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getCarRimOrderId()).isEqualTo("cro-2");
        assertThat(dto.getCarRim()).isNotNull();
        assertThat(dto.getCarRim().getRimName()).isEqualTo("Classic");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarRimOrder entity = new CarRimOrder();
        entity.setCarRimOrderId("Original-ID");
        CarRim originalRim = new CarRim();
        originalRim.setRimName("Original Name");
        entity.setCarRim(originalRim);

        CarRimOrderDto patchDto = new CarRimOrderDto();
        CarRimDto patchRimDto = new CarRimDto();
        patchRimDto.setRimName("Updated Name");
        patchDto.setCarRim(patchRimDto);
        patchDto.setCarRimOrderId(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getCarRimOrderId()).isEqualTo("Original-ID"); // Unchanged
        assertThat(entity.getCarRim().getRimName()).isEqualTo("Updated Name"); // Updated
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
