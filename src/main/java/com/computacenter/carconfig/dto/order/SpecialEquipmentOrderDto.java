package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.web.SpecialEquipmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialEquipmentOrderDto {
    private String specialEquipmentOrderId;
    private SpecialEquipmentDto specialEquipment;
    private CarOrderStatusDto orderStatus;
}
