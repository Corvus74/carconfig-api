package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.OrderStatusRepository;
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
