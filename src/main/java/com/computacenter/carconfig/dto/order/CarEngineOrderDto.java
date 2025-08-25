package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.web.CarEngineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEngineOrderDto {
    private String orderId;
    private CarEngineDto carEngine;
    private CarOrderStatusDto orderStatus;
}
