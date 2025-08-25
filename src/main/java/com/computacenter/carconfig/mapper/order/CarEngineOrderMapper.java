package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.CarEngineOrderDto;
import com.computacenter.carconfig.entities.order.CarEngineOrder;
import com.computacenter.carconfig.mapper.CarEngineMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarEngineMapper.class)
public interface CarEngineOrderMapper {

    CarEngineOrder toEntity(CarEngineOrderDto carEngineOrderDto);
    CarEngineOrderDto toDto(CarEngineOrder carEngineOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngineOrder partialUpdate(CarEngineOrderDto carEngineOrderDto, @MappingTarget CarEngineOrder carEngineOrder);
}