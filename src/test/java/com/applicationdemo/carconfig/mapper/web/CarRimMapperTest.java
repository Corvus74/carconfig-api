package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.CarRimDto;
import com.applicationdemo.carconfig.entities.base.CarRim;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class CarRimMapperTest {

    @Autowired
    private CarRimMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        CarRimDto dto = new CarRimDto();
        dto.setRimName("Sport");
        dto.setProductId("P-R01");

        // Act
        CarRim entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getRimName()).isEqualTo("Sport");
        assertThat(entity.getProductId()).isEqualTo("P-R01");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        CarRim entity = new CarRim();
        entity.setRimName("Classic");
        entity.setProductId("P-R02");

        // Act
        CarRimDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getRimName()).isEqualTo("Classic");
        assertThat(dto.getProductId()).isEqualTo("P-R02");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        CarRim entity = new CarRim();
        entity.setRimName("Original Name");
        entity.setPrice(2000);

        CarRimDto patchDto = new CarRimDto();
        patchDto.setRimName("Updated Name");
        patchDto.setPrice(null); // Should be ignored

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getRimName()).isEqualTo("Updated Name");
        assertThat(entity.getPrice()).isEqualTo(2000); // Unchanged
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
