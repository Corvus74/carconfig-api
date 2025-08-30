package com.computacenter.carconfig.entities.order;

import com.computacenter.carconfig.entities.SimpleAuditClasses;
import com.computacenter.carconfig.entities.base.CarRim;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_rims_order")
public class CarRimOrder extends SimpleAuditClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "car_rim_order_id")
    private String carRimOrderId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_rim_id")
    private CarRim carRim;

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
        CarRimOrder that = (CarRimOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(carRimOrderId, that.carRimOrderId) && Objects.equals(carRim, that.carRim) && Objects.equals(orderStatus, that.orderStatus) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, carRimOrderId, carRim, orderStatus, deleteFlag);
    }
}