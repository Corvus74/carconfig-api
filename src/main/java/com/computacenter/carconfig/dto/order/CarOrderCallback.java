package com.computacenter.carconfig.dto.order;


import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.dto.web.SpecialEquipmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderCallback {
    private OrderUserDto userId;
    private String carEngineProductId;
    private List<SpecialEquipmentDto> specialEquipmentId;
    private CarColorOrderDto carColorProductId;


}
