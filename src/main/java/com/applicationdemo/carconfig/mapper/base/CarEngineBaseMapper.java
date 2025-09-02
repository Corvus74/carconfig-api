package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.CarEngineLoadDto;
import com.applicationdemo.carconfig.entities.base.CarEngine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarEngineBaseMapper {
    CarEngine toEntity(CarEngineLoadDto carEngineDto);

    CarEngineLoadDto toDto(CarEngine carEngine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngine partialUpdate(CarEngineLoadDto carEngineDto, @MappingTarget CarEngine carEngine);
}