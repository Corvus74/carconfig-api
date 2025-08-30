package com.computacenter.carconfig.services.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.order.OrderStatus;
import com.computacenter.carconfig.enums.OrderStatusEnum;
import com.computacenter.carconfig.repository.order.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderStatusRepository orderStatusRepository;

    public OrderStatus createNewOrderStatusWithUser(OrderUser orderUser) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCurrentStatus(OrderStatusEnum.RECEIVED);
        orderStatus.setCreatedBy(orderUser.getUserName());
        orderStatus.setUpdatedBy(orderUser.getUserName());
        return orderStatusRepository.save(orderStatus);
    }

}
