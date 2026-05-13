package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatus extends SimpleAuditClasses {

    @Id
    @Column(name = "order_status_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderStatusId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_status")
    private OrderStatusEnum currentStatus;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(orderStatusId, that.orderStatusId) && currentStatus == that.currentStatus && Objects.equals(shippingDate, that.shippingDate) && Objects.equals(deliveryDate, that.deliveryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderStatusId, currentStatus, shippingDate, deliveryDate);
    }
}