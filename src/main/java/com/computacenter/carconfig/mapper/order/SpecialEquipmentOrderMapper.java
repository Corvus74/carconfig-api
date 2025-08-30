package com.computacenter.carconfig.mapper.order;

import com.computacenter.carconfig.dto.order.SpecialEquipmentOrderDto;
import com.computacenter.carconfig.entities.order.SpecialEquipmentOrder;
import com.computacenter.carconfig.mapper.web.SpecialEquipmentMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = SpecialEquipmentMapper.class)
public interface SpecialEquipmentOrderMapper {

    SpecialEquipmentOrder toEntity(SpecialEquipmentOrderDto specialEquipmentOrderDto);
    SpecialEquipmentOrderDto toDto(SpecialEquipmentOrder specialEquipmentOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipmentOrder partialUpdate(SpecialEquipmentOrderDto specialEquipmentOrderDto, @MappingTarget SpecialEquipmentOrder specialEquipmentOrder);
}