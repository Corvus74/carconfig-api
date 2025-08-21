package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.dto.base.CarEngineDto;
import com.computacenter.carconfig.entities.pool.CarEngine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarEngineMapper {
    CarEngine toEntity(CarEngineDto carEngineDto);

    CarEngineDto toDto(CarEngine carEngine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngine partialUpdate(CarEngineDto carEngineDto, @MappingTarget CarEngine carEngine);
}