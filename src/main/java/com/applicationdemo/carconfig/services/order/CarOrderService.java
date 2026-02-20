package com.applicationdemo.carconfig.services.order;

import com.applicationdemo.carconfig.dto.order.CarOrderDto;
import com.applicationdemo.carconfig.dto.order.CarOrderUpdateDto;
import com.applicationdemo.carconfig.domain.OrderUser;
import com.applicationdemo.carconfig.domain.order.CarOrder;
import com.applicationdemo.carconfig.domain.order.SpecialEquipmentOrder;
import com.applicationdemo.carconfig.exceptions.OrderException;
import com.applicationdemo.carconfig.mapper.order.CarOrderMapper;
import com.applicationdemo.carconfig.repositories.order.OrderRepository;
import com.applicationdemo.carconfig.services.OrderUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service class for handling business logic related to orders.
 * This class orchestrates data flow between the controller and the repository.
 */
@Service
@RequiredArgsConstructor
public class CarOrderService {

    private final OrderRepository orderRepository;
    private final OrderUserService orderUserService;

    private final CarOrderMapper orderMapper;

    private final CarColorOrderService carColorOrderService;
    private final CarEngineOrderService carEngineOrderService;
    private final CarRimOrderService carRimOrderService;
    private final SpecialEquipmentOrderService specialEquipmentOrderService;
    private final OrderStatusService orderStatusService;


    /**
     * Creates a new order by converting a DTO to an entity and saving it.
     *
     * @param orderDto The DTO containing the order data.
     */
    @Transactional
    public String createOrderByIds(CarOrderUpdateDto orderDto) {
        var orderUser = orderUserService.getUserIfExistsIfNotCreateUnknownUser(orderDto.getUserMail());
        var orderStatus = orderStatusService.createNewOrderStatusWithUser(orderUser);
        var carColorOrder = carColorOrderService.createCarColorsOrderByProductIdAndUser(orderDto.getCarColorProductId(), orderUser);
        var carEngineOrder = carEngineOrderService.createCarEngineOrderByProductIdAndUser(orderDto.getCarEngineProductId(), orderUser);
        var carRimOrder = carRimOrderService.createCarRimOrderProductIdAndOrderUser(orderDto.getCarRimsProductId(), orderUser);
        var specialEquipmentOrder = specialEquipmentOrderService.createSpecialEquipmentsOrdersByProductIdsAndUser(
                orderDto.getSpecialEquipmentProductIds() == null ? Collections.emptyList() : orderDto.getSpecialEquipmentProductIds(),
                orderUser
        );
        var order = new CarOrder();
        order.setCarOrderId(UUID.randomUUID().toString());
        order.setCarColorOrder(carColorOrder);
        order.setCarEngineOrder(carEngineOrder);
        order.setCarRimOrder(carRimOrder);
        order.setSpecialEquipmentOrders(specialEquipmentOrder);
        order.setOrderStatus(orderStatus);
        order.setOrderUser(orderUser);
        var savedOrder = orderRepository.save(order);
        return savedOrder.getCarOrderId();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return The CarOrderDto if found, otherwise null.
     */
    public Optional<CarOrderDto> getOrderById(String orderId) {
        Optional<CarOrder> orderOptional = orderRepository.findByCarOrderId(orderId);
        return orderOptional.map(orderMapper::toDto);
    }

    /**
     * Updates an existing order.
     *
     * @param orderDto The DTO with the updated data.
     */
    @Transactional
    public String updateOrder(CarOrderUpdateDto orderDto) {
        var existingOrderOpt = orderRepository.findByCarOrderId(orderDto.getCarOrderId());
        if (existingOrderOpt.isEmpty()) {
            throw new OrderException("CarOrder not found with id: " + orderDto.getCarOrderId());
        }
        var existingOrder = existingOrderOpt.get();
        var orderUpdateHelper = new CarOrderUpdateHelper();
        updateHelperIfUserHasChanged(orderUpdateHelper, orderDto);
        updateHelperIfColorHasChanged(orderDto, existingOrder, orderUpdateHelper);
        updateHelperIfCarEngineHasUpdated(orderDto, existingOrder, orderUpdateHelper);
        updateHelperIfCarRimHasUpdated(orderDto, existingOrder, orderUpdateHelper);
        updateHelperIfSpecialEquipmentHasChanged(orderDto, existingOrder, orderUpdateHelper);
        if (orderUpdateHelper.hasChanged()) {
            invalidateThisOrder(existingOrder);
            return createUpdatedOrderByIds(orderUpdateHelper,existingOrder);
        }
        return existingOrder.getCarOrderId();
    }

    private String createUpdatedOrderByIds(CarOrderUpdateHelper orderUpdateHelper, CarOrder existingOrder) {
        var order = new CarOrder();
        order.setCarOrderId(UUID.randomUUID().toString());
        order.setCarColorOrder(orderUpdateHelper.getExistingCarOrderColor());
        order.setCarEngineOrder(orderUpdateHelper.getExistingCarOrderEngine());
        order.setCarRimOrder(orderUpdateHelper.getExistingCarOrderRim());
        order.setSpecialEquipmentOrders(orderUpdateHelper.getExistingCarOrderSpecialEquipment());
        order.setOrderStatus(orderStatusService.createNewOrderStatusWithUser(orderUpdateHelper.getExistingOrderUser()));
        order.setOrderUser(orderUpdateHelper.getExistingOrderUser());
        order.setDescription("updated from order"+ existingOrder.getCarOrderId());

        var savedOrder = orderRepository.save(order);
        return savedOrder.getCarOrderId();
    }

    private void invalidateThisOrder(CarOrder existingOrder) {
        existingOrder.setDeleteFlag("Y");
        orderRepository.save(existingOrder);
    }

    private void updateHelperIfUserHasChanged(CarOrderUpdateHelper orderUpdateHelper, CarOrderUpdateDto orderDto) {
        // Resolve user (use anonymous if none provided)
        Optional<OrderUser> user = orderUserService.getOrderUserByMail(orderDto.getUserMail());
        if (user.isEmpty()) {
            return;
        }
        var orderUser = orderUserService.getUserIfExistsIfNotCreateUnknownUser(orderDto.getUserMail());
        orderUpdateHelper.setOrderUser(orderUser);
    }

    private void updateHelperIfColorHasChanged(CarOrderUpdateDto orderDto, CarOrder existingOrder, CarOrderUpdateHelper orderUpdateHelper) {
        if (Objects.nonNull(orderDto.getCarColorProductId())) {
            var invalidateCarColorIfProductIdDiffers = carColorOrderService.invalidateCarColorIfProductIdDiffers(existingOrder.getCarColorOrder(), orderDto.getCarColorProductId());
            if (invalidateCarColorIfProductIdDiffers) {
                var updatedOrder = carColorOrderService.createCarColorsOrderByProductIdAndUser(orderDto.getCarColorProductId(), orderUpdateHelper.getOrderUser());
                orderUpdateHelper.setCarColorOrder(updatedOrder);
            }
        }
    }


    private void updateHelperIfSpecialEquipmentHasChanged(CarOrderUpdateDto orderDto, CarOrder existingOrder, CarOrderUpdateHelper orderUpdateHelper) {
        if (Objects.nonNull(existingOrder.getSpecialEquipmentOrders())) {
            var anySpecialObjectHasChanged = specialEquipmentOrderService.invalidateSpecialEquipmentIfProductIdDiffers(existingOrder.getSpecialEquipmentOrders(), orderDto.getSpecialEquipmentProductIds());
            var oldOrder = getOldOrdersNotChanged(existingOrder.getSpecialEquipmentOrders(), orderDto.getSpecialEquipmentProductIds());
            if (anySpecialObjectHasChanged) {
                var updatedOrder = specialEquipmentOrderService.createSpecialEquipmentsOrdersByProductIdsAndUser(orderDto.getSpecialEquipmentProductIds(), orderUpdateHelper.getOrderUser());
                oldOrder.addAll(updatedOrder);
                orderUpdateHelper.setSpecialEquipmentOrders(oldOrder);

            }

        }
    }

    private List<SpecialEquipmentOrder> getOldOrdersNotChanged(List<SpecialEquipmentOrder> specialEquipmentOrders, List<String> specialEquipmentProductIds) {
        var oldOrder = new ArrayList<SpecialEquipmentOrder>();
        for (SpecialEquipmentOrder specialEquipmentOrder : specialEquipmentOrders) {
            if (!specialEquipmentProductIds.contains(specialEquipmentOrder.getSpecialEquipment().getProductId())) {
                oldOrder.add(specialEquipmentOrder);
            }
        }
        return oldOrder;
    }

    private void updateHelperIfCarEngineHasUpdated(CarOrderUpdateDto orderDto, CarOrder existingOrder, CarOrderUpdateHelper orderUpdateHelper) {
        if (Objects.nonNull(orderDto.getCarEngineProductId())) {
            var invalidateCarEngineIfProductIdDiffers = carEngineOrderService.invalidateCarEngineIfProductIdDiffers(existingOrder.getCarEngineOrder(), orderDto.getCarEngineProductId());
            if (invalidateCarEngineIfProductIdDiffers) {
                var updatedOrder = carEngineOrderService.createCarEngineOrderByProductIdAndUser(orderDto.getCarEngineProductId(), orderUpdateHelper.getOrderUser());
                orderUpdateHelper.setCarEngineOrder(updatedOrder);
            }
        }
    }

    private void updateHelperIfCarRimHasUpdated(CarOrderUpdateDto orderDto, CarOrder existingOrder, CarOrderUpdateHelper orderUpdateHelper) {
        if (Objects.nonNull(orderDto.getCarRimsProductId())) {
            var invalidateCarRimIfProductIdDiffers = carRimOrderService.invalidateCarRimIfProductIdDiffers(existingOrder.getCarRimOrder(), orderDto.getCarRimsProductId());
            if (invalidateCarRimIfProductIdDiffers) {
                var updatedOrder = carRimOrderService.createCarRimOrderProductIdAndOrderUser(orderDto.getCarRimsProductId(), orderUpdateHelper.getOrderUser());
                orderUpdateHelper.setCarRimOrder(updatedOrder);
            }
        }
    }


    /**
     * Deletes an order by its public orderId.
     *
     * @param orderId The public orderId of the order to delete.
     */
    @Transactional
    public void deleteOrder(String orderId) {
        var orderOpt = orderRepository.findByCarOrderId(orderId);
        if (orderOpt.isEmpty()) {
            throw new OrderException("CarOrder not found with id: " + orderId);
        }
        orderRepository.delete(orderOpt.get());
    }


}