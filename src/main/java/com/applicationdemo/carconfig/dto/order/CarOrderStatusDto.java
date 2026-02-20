package com.applicationdemo.carconfig.dto.order;

import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
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
public class CarOrderStatusDto {
    private OrderStatusEnum currentStatus;
    private LocalDate shippingDate;
    private LocalDate deliveryDate;

}