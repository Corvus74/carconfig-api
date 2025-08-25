package com.computacenter.carconfig.services.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.CarEngine;
import com.computacenter.carconfig.entities.order.CarEngineOrder;
import com.computacenter.carconfig.repository.order.CarEngineOrderRepository;
import com.computacenter.carconfig.services.pool.CarEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        carEngineOrder.setCreatedBy(orderUser.getUserName());
        carEngineOrder.setModifiedBy(orderUser.getUserName());
        carEngineOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return carEngineOrderRepository.save(carEngineOrder);
    }
}
