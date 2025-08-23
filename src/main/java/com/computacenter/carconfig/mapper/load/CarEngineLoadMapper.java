package com.computacenter.carconfig.mapper.load;

import com.computacenter.carconfig.dto.load.CarEngineLoadDto;
import com.computacenter.carconfig.dto.web.CarEngineDto;
import com.computacenter.carconfig.entities.base.CarEngine;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarEngineLoadMapper {
    CarEngine toEntity(CarEngineLoadDto carEngineDto);

    CarEngineLoadDto toDto(CarEngine carEngine);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarEngine partialUpdate(CarEngineLoadDto carEngineDto, @MappingTarget CarEngine carEngine);
}