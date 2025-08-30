package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.CarOrderDto;
import com.computacenter.carconfig.entities.order.CarOrder;
import com.computacenter.carconfig.mapper.OrderUserMapper;
import org.mapstruct.*;

/**
 * MapStruct mapper for converting between CarOrder entity and CarOrderDto.
 *
 * Notes:
 * - Uses nested mappers to map associated aggregates (user, engine, rims, color, status, special equipment orders).
 * - Ignores unmapped targets to tolerate DTO/Entity drift.
 * - Spring component model allows injection as a Spring bean.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {
                SpecialEquipmentOrderMapper.class,
                OrderUserMapper.class,
                CarEngineOrderMapper.class,
                CarColorOrderMapper.class,
                CarRimOrderMapper.class,
                OrderStatusMapper.class
        }
)
public interface OrderMapper {
    /** Converts a DTO to an entity. */
    CarOrder toEntity(CarOrderDto carOrderDto);

    /** Converts an entity to a DTO. */
    CarOrderDto toDto(CarOrder carOrder);

    /**
     * Partially updates the target entity with non-null values from the DTO.
     * Nested objects are updated using their own mappers.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarOrder partialUpdate(CarOrderDto carOrderDto, @MappingTarget CarOrder carOrder);
}