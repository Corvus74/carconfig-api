package com.computacenter.carconfig.services.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.CarRim;
import com.computacenter.carconfig.entities.order.CarRimOrder;
import com.computacenter.carconfig.repository.order.CarRimOrderRepository;
import com.computacenter.carconfig.services.base.CarRimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarRimOrderService {

    private final CarRimService carRimService;
    private final CarRimOrderRepository carRimOrderRepository;
    private final OrderStatusService orderStatusService;

    @Transactional
    public CarRimOrder createCarRimOrderProductIdAndOrderUser(String productId, OrderUser orderUser) {
        var originalRimProduct = carRimService.getCarRimByProductId(productId);
        return createCarRimOrderByCarRimAndOrderUser(originalRimProduct, orderUser);
    }

    private CarRimOrder createCarRimOrderByCarRimAndOrderUser(CarRim carRim, OrderUser orderUser) {
        var carRimOrder = new CarRimOrder();
        carRimOrder.setCarRim(carRim);
        carRimOrder.setCarRimOrderId(UUID.randomUUID().toString());
        carRimOrder.setCreatedBy(orderUser.getUserName());
        carRimOrder.setUpdatedBy(orderUser.getUserName());
        carRimOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return carRimOrderRepository.save(carRimOrder);
    }

}
