package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarEngineMapper {
    CarEngine toEntity(CarEngineDto carEngineDto);

    CarEngineDto toDto(CarEngine carEngine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngine partialUpdate(CarEngineDto carEngineDto, @MappingTarget CarEngine carEngine);
}