package com.computacenter.carconfig.mapper.load;

import com.computacenter.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.computacenter.carconfig.dto.web.SpecialEquipmentDto;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialEquipmentLoadMapper {
    SpecialEquipment toEntity(SpecialEquipmentLoadDto specialEquipmentDto);

    SpecialEquipmentLoadDto toDto(SpecialEquipment specialEquipment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipment partialUpdate(SpecialEquipmentLoadDto specialEquipmentDto, @MappingTarget SpecialEquipment specialEquipment);
}