package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.user.OrderUser;
import com.applicationdemo.carconfig.domain.order.OrderStatus;
import com.applicationdemo.carconfig.enums.OrderStatusEnum;
import com.applicationdemo.carconfig.repositories.order.OrderStatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceTest {

    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    @Test
    void createNewOrderStatusWithUser() {
        OrderUser user = new OrderUser();
        user.setUserName("testuser");

        OrderStatus savedStatus = new OrderStatus();
        when(orderStatusRepository.save(any(OrderStatus.class))).thenReturn(savedStatus);

        OrderStatus result = orderStatusService.createNewOrderStatusWithUser(user);

        ArgumentCaptor<OrderStatus> orderStatusCaptor = ArgumentCaptor.forClass(OrderStatus.class);
        verify(orderStatusRepository).save(orderStatusCaptor.capture());

        OrderStatus capturedStatus = orderStatusCaptor.getValue();
        assertEquals(OrderStatusEnum.RECEIVED, capturedStatus.getCurrentStatus());
        assertEquals("testuser", capturedStatus.getCreatedBy());
        assertEquals("testuser", capturedStatus.getUpdatedBy());

        assertEquals(savedStatus, result);
    }
}
