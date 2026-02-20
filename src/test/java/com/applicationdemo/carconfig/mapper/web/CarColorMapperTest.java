package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.domain.base.CarColor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarColorMapperTest {

    @Autowired
    private CarColorMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarColorDto dto = new CarColorDto();
        dto.setColorName("Test Red");
        dto.setProductId("P-C01");

        // Act
        CarColor entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getColorName()).isEqualTo("Test Red");
        assertThat(entity.getProductId()).isEqualTo("P-C01");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarColor entity = new CarColor();
        entity.setColorName("Test Blue");
        entity.setProductId("P-C02");

        // Act
        CarColorDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getColorName()).isEqualTo("Test Blue");
        assertThat(dto.getProductId()).isEqualTo("P-C02");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarColor entity = new CarColor();
        entity.setColorName("Original Name");
        entity.setPrice(1000);

        CarColorDto patchDto = new CarColorDto();
        patchDto.setColorName("Updated Name");
        patchDto.setPrice(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getColorName()).isEqualTo("Updated Name");
        assertThat(entity.getPrice()).isEqualTo(1000); // Unchanged
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
