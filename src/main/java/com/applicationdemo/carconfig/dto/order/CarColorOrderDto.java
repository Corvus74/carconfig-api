package com.applicationdemo.carconfig.dto.order;

import com.applicationdemo.carconfig.dto.web.CarColorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarColorOrderDto {
    private String carColorOrderId;
    private CarColorDto carColor;
    private CarOrderStatusDto orderStatus;
}
