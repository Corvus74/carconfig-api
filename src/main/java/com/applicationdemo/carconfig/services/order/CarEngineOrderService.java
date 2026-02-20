package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarEngine;
import com.applicationdemo.carconfig.domain.order.CarEngineOrder;
import com.applicationdemo.carconfig.repositories.order.CarEngineOrderRepository;
import com.applicationdemo.carconfig.services.base.CarEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarEngineOrderService {
    private final CarEngineOrderRepository carEngineOrderRepository;
    private final CarEngineService carEngineService;
    private final OrderStatusService orderStatusService;

    @Transactional
    public CarEngineOrder createCarEngineOrderByProductIdAndUser(String productId, OrderUser orderUser) {
        var originalRimProduct = carEngineService.getCarEngineByProductId(productId);
        return createCarEngineOrderByEngineOrderAndUser(originalRimProduct, orderUser);
    }

    private CarEngineOrder createCarEngineOrderByEngineOrderAndUser(CarEngine carEngine, OrderUser orderUser) {
        var carEngineOrder = new CarEngineOrder();
        carEngineOrder.setCarEngine(carEngine);
        carEngineOrder.setCarEngineOrderId(UUID.randomUUID().toString());
        carEngineOrder.setCreatedBy(orderUser.getUserName());
        carEngineOrder.setUpdatedBy(orderUser.getUserName());
        carEngineOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return carEngineOrderRepository.save(carEngineOrder);
    }
    @Transactional
    public boolean invalidateCarEngineIfProductIdDiffers(CarEngineOrder existingOrder, String productId) {
        if( existingOrder.getCarEngine().getProductId().equals(productId)){
            return false;
        }
        existingOrder.setDeleteFlag("Y");
        //invalidate order
        carEngineOrderRepository.save(existingOrder);
        return true;
    }
}
