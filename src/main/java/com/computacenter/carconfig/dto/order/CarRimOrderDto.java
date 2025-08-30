package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.web.CarRimDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRimOrderDto {
    private String carRimOrderId;
    private CarRimDto carRim;
    private CarOrderStatusDto orderStatus;
}
