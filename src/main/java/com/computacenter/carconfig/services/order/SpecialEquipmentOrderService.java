package com.computacenter.carconfig.services.order;

import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.entities.order.SpecialEquipmentOrder;
import com.computacenter.carconfig.repository.order.SpecialEquipmentOrderRepository;
import com.computacenter.carconfig.services.base.SpecialEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecialEquipmentOrderService {

    private final SpecialEquipmentService specialEquipmentService;
    private final OrderStatusService orderStatusService;
    private final SpecialEquipmentOrderRepository specialEquipmentOrderRepository;


    @Transactional
    public List<SpecialEquipmentOrder> createSpecialEquipmentsOrdersByProductIdsAndUser(List<String> specialEquipmentProductIds, OrderUser orderUser) {
        var listOfMappedSpecialEquipments = specialEquipmentProductIds.stream().map(specialEquipmentService::getSpecialEquipmentByProductId).toList();
        return listOfMappedSpecialEquipments.stream().map(
                specialEquipment -> createNewSpecialEquipmentOrderBySpecialEquipmentAndUser(specialEquipment, orderUser)
        ).toList();
    }

    private SpecialEquipmentOrder createNewSpecialEquipmentOrderBySpecialEquipmentAndUser(SpecialEquipment specialEquipment, OrderUser orderUser) {
        var specialEquipmentOrder = new SpecialEquipmentOrder();
        specialEquipmentOrder.setSpecialEquipment(specialEquipment);
        specialEquipmentOrder.setSpecialEquipmentOrderId(UUID.randomUUID().toString());
        specialEquipmentOrder.setCreatedBy(orderUser.getUserName());
        specialEquipmentOrder.setUpdatedBy(orderUser.getUserName());
        specialEquipmentOrder.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUser));
        return specialEquipmentOrderRepository.save(specialEquipmentOrder);
    }

}
