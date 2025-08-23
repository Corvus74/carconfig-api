package com.computacenter.carconfig.dto;

import com.computacenter.carconfig.dto.order.CarColorOrderDto;
import com.computacenter.carconfig.dto.order.CarEngineOrderDto;
import com.computacenter.carconfig.dto.order.CarRimOrderDto;
import com.computacenter.carconfig.dto.order.SpecialEquipmentOrderDto;
import com.computacenter.carconfig.entities.order.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for {@link Order}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private String orderId;
    private OrderUserDto orderUser;
    private CarEngineOrderDto carEngineOrder;
    private CarRimOrderDto carRimOrder;
    private CarColorOrderDto carColorOrder;
    private OrderStatusDto orderStatus;
    private @Size(max = 400) String description;
    private Integer price;
    private List<SpecialEquipmentOrderDto> specialEquipmentOrders;

}