package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import com.applicationdemo.carconfig.domain.base.CarEngine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_engine_order")
public class CarEngineOrder extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "car_engine_order_id")
    private String carEngineOrderId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_engine_id")
    private CarEngine carEngine;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Size(max = 1)
    @Column(name = "delete_flag")
    private String deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarEngineOrder that = (CarEngineOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(carEngineOrderId, that.carEngineOrderId) && Objects.equals(carEngine, that.carEngine) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, carEngineOrderId, carEngine, orderStatus, deleteFlag);
    }
}