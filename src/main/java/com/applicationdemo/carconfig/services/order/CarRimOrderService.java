package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarRim;
import com.applicationdemo.carconfig.domain.order.CarRimOrder;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.CarRimOrderRepository;
import com.applicationdemo.carconfig.services.base.CarRimService;
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
    @Transactional
    public boolean invalidateCarRimIfProductIdDiffers(CarRimOrder existingOrder, String productId) {
        if( existingOrder.getCarRim().getProductId().equals(productId)){
            return false;
        }
        existingOrder.getOrderStatus().setCurrentStatus(OrderStatusEnum.CANCELLED);
        //invalidate order
        carRimOrderRepository.save(existingOrder);
        return true;
    }
}
