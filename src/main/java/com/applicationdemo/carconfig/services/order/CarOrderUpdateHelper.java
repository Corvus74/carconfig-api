package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.order.*;
import com.applicationdemo.carconfig.domain.user.OrderUser;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
//simpleHolder for Orders
public class CarOrderUpdateHelper {

    private OrderUser orderUser;
    private CarEngineOrder carEngineOrder;
    private CarRimOrder carRimOrder;
    private CarColorOrder carColorOrder;
    private List<SpecialEquipmentOrder> specialEquipmentOrders;
    private CarOrder carOrder;

    public boolean hasChanged() {
        return Objects.nonNull(orderUser) || Objects.nonNull(carEngineOrder) || Objects.nonNull(carRimOrder) || Objects.nonNull(carColorOrder) || Objects.nonNull(specialEquipmentOrders);
    }

    public CarColorOrder getExistingCarOrderColor() {
        if (Objects.isNull(carColorOrder)) {
            return carOrder.getCarColorOrder();
        }
        return carColorOrder;
    }

    public CarEngineOrder getExistingCarOrderEngine() {
        if (Objects.isNull(carEngineOrder)) {
            return carOrder.getCarEngineOrder();
        }
        return carEngineOrder;
    }

    public CarRimOrder getExistingCarOrderRim() {
        if (Objects.isNull(carColorOrder)) {
            return carOrder.getCarRimOrder();
        }
        return carOrder.getCarRimOrder();
    }

    public List<SpecialEquipmentOrder> getExistingCarOrderSpecialEquipment() {
        if (Objects.isNull(specialEquipmentOrders)) {
            return carOrder.getSpecialEquipmentOrders();
        }
        return specialEquipmentOrders;
    }

    public OrderUser getExistingOrderUser() {
        if (Objects.isNull(orderUser)) {
            return carOrder.getOrderUser();
        }
        return orderUser;
    }
}
