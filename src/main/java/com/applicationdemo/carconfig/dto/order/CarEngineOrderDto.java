package com.applicationdemo.carconfig.dto.order;

import com.applicationdemo.carconfig.dto.web.CarEngineDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarEngineOrderDto {
    private String carEngineOrderId;
    private CarEngineDto carEngine;
    private CarOrderStatusDto orderStatus;
}
