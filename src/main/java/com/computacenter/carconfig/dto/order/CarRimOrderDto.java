package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderStatusDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRimOrderDto {
    private CarRimOrderDto carRimOrder;
    private OrderStatusDto orderStatusDto;
}
