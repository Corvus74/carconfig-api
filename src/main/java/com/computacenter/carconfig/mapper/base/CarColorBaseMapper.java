package com.computacenter.carconfig.mapper.base;

import com.computacenter.carconfig.dto.load.CarColorLoadDto;
import com.computacenter.carconfig.entities.base.CarColor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarColorBaseMapper {
    CarColor toEntity(CarColorLoadDto carColorDto);

    CarColorLoadDto toDto(CarColor carColor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColor partialUpdate(CarColorLoadDto carColorLoadDto, @MappingTarget CarColor carColo);
}