package com.computacenter.carconfig.dto;


import com.computacenter.carconfig.dto.base.SpecialEquipmentDto;
import com.computacenter.carconfig.dto.order.CarColorOrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCallback {
    private OrderUserDto userId;
    private String carEngineProductId;
    private List<SpecialEquipmentDto> specialEquipmentId;
    private CarColorOrderDto carColorProductId;


}
