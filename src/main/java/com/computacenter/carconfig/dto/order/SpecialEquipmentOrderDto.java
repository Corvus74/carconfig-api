package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderStatusDto;
import com.computacenter.carconfig.dto.base.SpecialEquipmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialEquipmentOrderDto {
    private SpecialEquipmentDto specialEquipmentDto;
    private OrderStatusDto orderStatusDto;
}
