package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderStatusDto;
import com.computacenter.carconfig.dto.web.CarEngineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEngineOrderDto {
    private CarEngineDto carEngineDto;
    private OrderStatusDto orderStatusDto;
}
