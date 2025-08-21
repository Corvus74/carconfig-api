package com.computacenter.carconfig.mapper;

import com.computacenter.carconfig.dto.base.CarColorDto;
import com.computacenter.carconfig.entities.pool.CarColors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarColorsMapper {
    CarColors toEntity(CarColorDto carColorDto);

    CarColorDto toDto(CarColors carColors);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColors partialUpdate(CarColorDto carColorDto, @MappingTarget CarColors carColors);
}