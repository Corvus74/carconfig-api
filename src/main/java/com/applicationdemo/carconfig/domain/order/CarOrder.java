package com.applicationdemo.carconfig.domain.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.SimpleAuditClasses;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "car_order")
public class CarOrder extends SimpleAuditClasses {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 40)
    @Column(name = "car_order_id")
    private String carOrderId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_user_id")
    private OrderUser orderUser;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_engine_order_id")
    private CarEngineOrder carEngineOrder;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_rim_order_id")
    private CarRimOrder carRimOrder;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "car_color_order_id")
    private CarColorOrder carColorOrder;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @Size(max = 400)
    @Column(name = "description", length = 400)
    private String description;

    @Column(name = "total_price")
    private Integer totalPrice;

    @ManyToMany(fetch =  FetchType.LAZY)
    @JoinTable(
            name = "car_config_special_equipments_group",
            joinColumns = @JoinColumn(name = "car_order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "special_equipment_order_id", referencedColumnName = "id")
    )
    private List<SpecialEquipmentOrder> specialEquipmentOrders;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarOrder carOrder = (CarOrder) o;
        return Objects.equals(id, carOrder.id) && Objects.equals(carOrderId, carOrder.carOrderId) && Objects.equals(orderUser, carOrder.orderUser) && Objects.equals(carEngineOrder, carOrder.carEngineOrder) && Objects.equals(carRimOrder, carOrder.carRimOrder) && Objects.equals(carColorOrder, carOrder.carColorOrder) && Objects.equals(orderStatus, carOrder.orderStatus) && Objects.equals(description, carOrder.description) && Objects.equals(totalPrice, carOrder.totalPrice) && Objects.equals(specialEquipmentOrders, carOrder.specialEquipmentOrders);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, carOrderId, orderUser, carEngineOrder, carRimOrder, carColorOrder, orderStatus, description, totalPrice, specialEquipmentOrders);
    }
}