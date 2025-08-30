package com.computacenter.carconfig.mapper.base;

import com.computacenter.carconfig.dto.load.SpecialEquipmentLoadDto;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SpecialEquipmentBaseMapper {
    SpecialEquipment toEntity(SpecialEquipmentLoadDto specialEquipmentDto);

    SpecialEquipmentLoadDto toDto(SpecialEquipment specialEquipment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipment partialUpdate(SpecialEquipmentLoadDto specialEquipmentDto, @MappingTarget SpecialEquipment specialEquipment);
}