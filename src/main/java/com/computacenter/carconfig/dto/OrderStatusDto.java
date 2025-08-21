package com.computacenter.carconfig.dto;

import com.computacenter.carconfig.entities.order.OrderStatus;
import com.computacenter.carconfig.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO for {@link OrderStatus}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private Integer id;
    private OrderStatusEnum orderStatus;
    private LocalDate shippingDate;
    private LocalDate deliveryDate;

}