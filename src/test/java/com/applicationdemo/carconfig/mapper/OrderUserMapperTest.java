package com.applicationdemo.carconfig.mapper;

import com.applicationdemo.carconfig.dto.OrderUserDto;
import com.applicationdemo.carconfig.domain.user.OrderUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class OrderUserMapperTest {

    @Autowired
    private OrderUserMapper mapper;

    @Test
    void toEntity_mapsAllFields() {
        // Arrange
        OrderUserDto dto = new OrderUserDto("testuser", "test@example.com");

        // Act
        OrderUser entity = mapper.toEntity(dto);

        // Assert
        assertThat(entity).isNotNull();
        assertThat(entity.getUserName()).isEqualTo("testuser");
        assertThat(entity.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void toDto_mapsAllFields() {
        // Arrange
        OrderUser entity = new OrderUser();
        entity.setUserName("testuser2");
        entity.setEmail("test2@example.com");

        // Act
        OrderUserDto dto = mapper.toDto(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getUserName()).isEqualTo("testuser2");
        assertThat(dto.getEmail()).isEqualTo("test2@example.com");
    }

    @Test
    void partialUpdate_updatesOnlyNonNullFields() {
        // Arrange
        OrderUser entity = new OrderUser();
        entity.setUserName("OriginalName");
        entity.setEmail("original@example.com");

        OrderUserDto patchDto = new OrderUserDto("UpdatedName", null);

        // Act
        mapper.partialUpdate(patchDto, entity);

        // Assert
        assertThat(entity.getUserName()).isEqualTo("UpdatedName");
        assertThat(entity.getEmail()).isEqualTo("original@example.com"); // Unchanged
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
