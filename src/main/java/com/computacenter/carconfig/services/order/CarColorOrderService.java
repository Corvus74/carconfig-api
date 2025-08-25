package com.computacenter.carconfig.services.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.CarColor;
import com.computacenter.carconfig.entities.order.CarColorOrder;
import com.computacenter.carconfig.repository.order.CarColorOrderRepository;
import com.computacenter.carconfig.services.pool.CarColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        carColorsOrder.setCreatedBy(orderUser.getUserName());
        carColorsOrder.setModifiedBy(orderUser.getUserName());
        carColorsOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return carColorOrderRepository.save(carColorsOrder);
    }
}
