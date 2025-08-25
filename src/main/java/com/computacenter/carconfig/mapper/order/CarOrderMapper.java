package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.CarOrderDto;
import com.computacenter.carconfig.entities.order.CarOrder;
import com.computacenter.carconfig.mapper.OrderUserMapper;
import org.mapstruct.*;

/**
 * Alternative mapper for CarOrder. Retained for compatibility but OrderMapper is used in services.
 */
@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses ={
                OrderUserMapper.class,
                CarEngineOrderMapper.class,
                CarColorOrderMapper.class,
                CarRimOrderMapper.class,
                SpecialEquipmentOrderMapper.class,
                OrderStatusMapper.class
        }
)
public interface CarOrderMapper {
    CarOrder toEntity(CarOrderDto carOrderDto);

    CarOrderDto toDto(CarOrder carOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarOrder partialUpdate(CarOrderDto carOrderDto, @MappingTarget CarOrder carOrder);
}