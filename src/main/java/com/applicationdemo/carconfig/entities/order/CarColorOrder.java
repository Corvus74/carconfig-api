package com.applicationdemo.carconfig.entities.order;

import com.applicationdemo.carconfig.entities.SimpleAuditClasses;
import com.applicationdemo.carconfig.entities.base.CarColor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_colors_order")
public class CarColorOrder extends SimpleAuditClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "car_color_order_id")
    private String carColorOrderId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_color_id")
    private CarColor carColor;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Size(max = 1)
    @Column(name = "delete_flag", length = 1)
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarColorOrder that = (CarColorOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(carColorOrderId, that.carColorOrderId) && Objects.equals(carColor, that.carColor) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, carColorOrderId, carColor, orderStatus, deleteFlag);
    }
}