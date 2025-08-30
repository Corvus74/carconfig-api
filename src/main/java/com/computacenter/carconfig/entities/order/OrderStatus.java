package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.SimpleAuditClasses;
import com.computacenter.carconfig.enums.OrderStatusEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatus extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "current_status")
    private OrderStatusEnum currentStatus;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderStatus that = (OrderStatus) o;
        return Objects.equals(id, that.id) && currentStatus == that.currentStatus && Objects.equals(shippingDate, that.shippingDate) && Objects.equals(deliveryDate, that.deliveryDate) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, currentStatus, shippingDate, deliveryDate, deleteFlag);
    }
}