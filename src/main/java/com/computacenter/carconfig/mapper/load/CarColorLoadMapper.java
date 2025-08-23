package com.computacenter.carconfig.mapper.load;

import com.computacenter.carconfig.dto.load.CarColorLoadDto;
import com.computacenter.carconfig.dto.web.CarColorDto;
import com.computacenter.carconfig.entities.base.CarColor;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarColorLoadMapper {
    CarColor toEntity(CarColorLoadDto carColorDto);

    CarColorLoadDto toDto(CarColor carColor);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CarColor partialUpdate(CarColorLoadDto carColorLoadDto, @MappingTarget CarColor carColo);
}