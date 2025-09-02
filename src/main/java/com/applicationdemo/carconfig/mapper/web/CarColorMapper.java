package com.applicationdemo.carconfig.mapper.web;

import com.applicationdemo.carconfig.dto.web.CarColorDto;
import com.applicationdemo.carconfig.entities.base.CarColor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarColorMapper {
    CarColor toEntity(CarColorDto carColorDto);

    CarColorDto toDto(CarColor carColor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColor partialUpdate(CarColorDto carColorDto, @MappingTarget CarColor carColor);
}