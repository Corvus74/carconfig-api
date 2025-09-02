package com.applicationdemo.carconfig.mapper.base;

import com.applicationdemo.carconfig.dto.load.CarColorLoadDto;
import com.applicationdemo.carconfig.entities.base.CarColor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarColorBaseMapper {
    CarColor toEntity(CarColorLoadDto carColorDto);

    CarColorLoadDto toDto(CarColor carColor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColor partialUpdate(CarColorLoadDto carColorLoadDto, @MappingTarget CarColor carColo);
}