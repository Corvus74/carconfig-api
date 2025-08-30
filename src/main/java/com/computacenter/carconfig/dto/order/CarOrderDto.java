package com.computacenter.carconfig.dto.order;

import com.computacenter.carconfig.dto.OrderUserDto;
import com.computacenter.carconfig.entities.order.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link CarOrder}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarOrderDto {
    private String carOrderId;
    private OrderUserDto orderUser;
    private CarEngineOrderDto carEngineOrder;
    private CarRimOrderDto carRimOrder;
    private CarColorOrderDto carColorOrder;
    private CarOrderStatusDto orderStatus;
    private String description;
    private Integer totalPrice;
    private List<SpecialEquipmentOrderDto> specialEquipmentOrders;
    private Instant createdAt;
    private Instant updatedAt;

}