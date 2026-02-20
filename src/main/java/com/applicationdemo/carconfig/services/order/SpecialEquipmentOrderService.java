package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.domain.base.SpecialEquipment;
import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import com.applicationdemo.carconfig.repositories.order.SpecialEquipmentOrderRepository;
import com.applicationdemo.carconfig.services.base.SpecialEquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public boolean invalidateSpecialEquipmentIfProductIdDiffers(List<SpecialEquipmentOrder> existingOrders, List<String> newProductIds) {
        var oldIsEmpty= Objects.isNull(existingOrders) || existingOrders.isEmpty();
        if(oldIsEmpty){
            return true;
        }
        var oldProductIds = existingOrders.stream().map(SpecialEquipmentOrder::getSpecialEquipment).map(SpecialEquipment::getProductId).toList();
        if(oldProductIds.equals(newProductIds)){
            return false;
        }
        return invalidateSingleEquipmentIdsDiffer(existingOrders,newProductIds);

    }

    private boolean invalidateSingleEquipmentIdsDiffer(List<SpecialEquipmentOrder> existingOrders, List<String> newProductIds) {
        existingOrders.forEach(specialEquipmentOrder -> {
            if(!newProductIds.contains(specialEquipmentOrder.getSpecialEquipment().getProductId())){
                specialEquipmentOrder.setDeleteFlag("Y");
                specialEquipmentOrderRepository.save(specialEquipmentOrder);
            }
        });
        return true;

    }

    private void invalidateAllOldOrders(List<SpecialEquipmentOrder> existingOrders) {
        existingOrders.forEach(specialEquipmentOrder -> {
            specialEquipmentOrder.setDeleteFlag("Y");
            specialEquipmentOrderRepository.save(specialEquipmentOrder);
        });
    }

    /**
     *  2nd part of the update process.
     * @param existingOrders
     * @param newProductIds
     * @param orderUser
     * @return
     */
    @Transactional
    //Because ofTransaction annotation, the updateSpecialEquipmentsOrdersByProductIdsAndUser method is called before the invalidateSpecialEquipmentIfProductIdDiffers method.
    public List<SpecialEquipmentOrder> updateSpecialEquipmentsOrdersByProductIdsAndUser(List<SpecialEquipmentOrder> existingOrders, List<String> newProductIds, OrderUser orderUser) {
        var toBeAddendOrderIds = new ArrayList<String>();
        for (SpecialEquipmentOrder existingOrder : existingOrders) {
            var equipment = existingOrder.getSpecialEquipment();
            String productId = equipment.getProductId();
            if( !newProductIds.contains(productId)) {
                toBeAddendOrderIds.add(productId);
            }
        }
        var listOfMappedSpecialEquipments = toBeAddendOrderIds.stream().map(specialEquipmentService::getSpecialEquipmentByProductId).toList();
        return listOfMappedSpecialEquipments.stream().map(
                specialEquipment -> createNewSpecialEquipmentOrderBySpecialEquipmentAndUser(specialEquipment, orderUser)
        ).toList();
    }

}
