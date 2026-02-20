package com.applicationdemo.carconfig.mapper.order;

import com.applicationdemo.carconfig.dto.order.SpecialEquipmentOrderDto;
import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import com.applicationdemo.carconfig.mapper.web.SpecialEquipmentMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = SpecialEquipmentMapper.class)
public interface SpecialEquipmentOrderMapper {

    SpecialEquipmentOrder toEntity(SpecialEquipmentOrderDto specialEquipmentOrderDto);
    SpecialEquipmentOrderDto toDto(SpecialEquipmentOrder specialEquipmentOrder);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SpecialEquipmentOrder partialUpdate(SpecialEquipmentOrderDto specialEquipmentOrderDto, @MappingTarget SpecialEquipmentOrder specialEquipmentOrder);
}