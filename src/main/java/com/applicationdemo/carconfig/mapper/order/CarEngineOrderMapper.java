package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.CarEngineOrderDto;
import com.applicationdemo.carconfig.domain.order.CarEngineOrder;
import com.applicationdemo.carconfig.mapper.web.CarEngineMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = CarEngineMapper.class)
public interface CarEngineOrderMapper {

    CarEngineOrder toEntity(CarEngineOrderDto carEngineOrderDto);
    CarEngineOrderDto toDto(CarEngineOrder carEngineOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngineOrder partialUpdate(CarEngineOrderDto carEngineOrderDto, @MappingTarget CarEngineOrder carEngineOrder);
}