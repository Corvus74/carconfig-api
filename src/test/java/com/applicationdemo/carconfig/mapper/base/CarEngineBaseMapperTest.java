package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.CarEngineLoadDto;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarEngineBaseMapperTest {

    @Autowired
    private CarEngineBaseMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarEngineLoadDto dto = new CarEngineLoadDto();
        dto.setModel("V8");
        dto.setProductId("P-E01");

        // Act
        CarEngine entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getModel()).isEqualTo("V8");
        assertThat(entity.getProductId()).isEqualTo("P-E01");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarEngine entity = new CarEngine();
        entity.setModel("V6");
        entity.setProductId("P-E02");

        // Act
        CarEngineLoadDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getModel()).isEqualTo("V6");
        assertThat(dto.getProductId()).isEqualTo("P-E02");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarEngine entity = new CarEngine();
        entity.setModel("Original Model");
        entity.setPrice(5000);

        CarEngineLoadDto patchDto = new CarEngineLoadDto();
        patchDto.setModel("Updated Model");
        patchDto.setPrice(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getModel()).isEqualTo("Updated Model");
        assertThat(entity.getPrice()).isEqualTo(5000); // Unchanged
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
