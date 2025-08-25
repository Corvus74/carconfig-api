package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.entities.order.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link CarOrder}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarOrderDto {
    private String orderId;
    private OrderUserDto orderUser;
    private CarEngineOrderDto carEngineOrder;
    private CarRimOrderDto carRimOrder;
    private CarColorOrderDto carColorOrder;
    private CarOrderStatusDto orderStatus;
    private @Size(max = 400) String description;
    private Integer price;
    private List<SpecialEquipmentOrderDto> specialEquipmentOrders;

}