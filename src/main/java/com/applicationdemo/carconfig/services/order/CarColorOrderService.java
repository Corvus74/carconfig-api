package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.base.CarColor;
import com.applicationdemo.carconfig.domain.order.CarColorOrder;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.CarColorOrderRepository;
import com.applicationdemo.carconfig.services.base.CarColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarColorOrderService {
    private final CarColorOrderRepository carColorOrderRepository;
    private final CarColorService carColorService;
    private final OrderStatusService orderStatusService;


    @Transactional
    public CarColorOrder createCarColorsOrderByProductIdAndUser(String productId, OrderUser orderUser) {
        var originalRimProduct = carColorService.getColorByProductId(productId);
        return createAndSaveCarColorOrderByOrderUser(originalRimProduct, orderUser);
    }

    private CarColorOrder createAndSaveCarColorOrderByOrderUser(CarColor carColor, OrderUser orderUser) {
        var carColorsOrder = new CarColorOrder();
        carColorsOrder.setCarColor(carColor);
        carColorsOrder.setCarColorOrderId(UUID.randomUUID().toString());
        carColorsOrder.setCreatedBy(orderUser.getUserName());
        carColorsOrder.setUpdatedBy(orderUser.getUserName());
        carColorsOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return carColorOrderRepository.save(carColorsOrder);
    }

    @Transactional
    public boolean invalidateCarColorIfProductIdDiffers(CarColorOrder existingOrder, String productId) {
        if( existingOrder.getCarColor().getProductId().equals(productId)){
            return false;
        }
        existingOrder.getOrderStatus().setCurrentStatus(OrderStatusEnum.CANCELLED);
        carColorOrderRepository.save(existingOrder);
        return true;
    }
}
