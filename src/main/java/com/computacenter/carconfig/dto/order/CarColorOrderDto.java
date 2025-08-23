package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderStatusDto;

import com.computacenter.carconfig.dto.web.CarColorDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarColorOrderDto {
    private CarColorDto carColorDto;
    private OrderStatusDto orderStatusDto;
}
