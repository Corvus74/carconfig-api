package com.computacenter.carconfig.services;

import com.computacenter.carconfig.dto.OrderUpdateDto;
import com.computacenter.carconfig.dto.OrderDto;
import com.computacenter.carconfig.entities.OrderUser;
import com.computacenter.carconfig.entities.base.CarColor;
import com.computacenter.carconfig.entities.base.CarEngine;
import com.computacenter.carconfig.entities.base.CarRim;
import com.computacenter.carconfig.entities.base.SpecialEquipment;
import com.computacenter.carconfig.entities.order.*;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.mapper.OrderMapper;
import com.computacenter.carconfig.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for handling business logic related to orders.
 * This class orchestrates data flow between the controller and the repository.
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CarColorService carColorService;
    private final CarEngineService carEngineService;
    private final CarRimsService carRimsService;
    private final UserService userService;
    private final SpecialEquipmentService specialEquipmentService;
    private final OrderMapper orderMapper;

    /**
     * Creates a new order by converting a DTO to an entity and saving it.
     *
     * @param orderDto The DTO containing the order data.
     */
    public void createOrderByIds(OrderUpdateDto orderDto) {
        var carColor = carColorService.getColorByProductId(orderDto.getCarColorProductId());
        var carEngine = carEngineService.getCarEngineByProductId(orderDto.getCarEngineProductId());
        var carRim = carRimsService.getCarRimByProductId(orderDto.getCarRimsProductId());
        var specialEquipments = getSpecialeEquipments(orderDto.getSpecialEquipmentProductIds());
        var orderUser = getUserIfExists(orderDto.getUserMail());
        var order = createOrder(orderUser, carColor, carEngine, carRim, specialEquipments);
        orderRepository.save(order);
    }

    private OrderUser getUserIfExists(String userMail) {
        if (StringUtils.isBlank(userMail)) {
            return UserService.getAnonymousUser();
        }
        var orderUser = userService.getOrderUserByMail(userMail);
        return orderUser.orElseGet(UserService::getAnonymousUser);
    }


    private Order createOrder(OrderUser orderUser, CarColor carColor, CarEngine carEngine, CarRim carRim, List<SpecialEquipment> specialEquipments) {
        var carColorOrder = createCarColorOrder(carColor, orderUser);
        var carEngineOrder = createCarEngineOrder(carEngine, orderUser);
        var carRimOrder = createCarRimOrder(carRim, orderUser);
        var specialEquipmentOrder = createSpecialEquipmentOrder(specialEquipments, orderUser);
        var orderStatus = createOrderStatus(orderUser);
        var order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCarColorOrder(carColorOrder);
        order.setCarEngineOrder(carEngineOrder);
        order.setCarRimOrder(carRimOrder);
        order.setSpecialEquipmentOrders(specialEquipmentOrder);
        order.setOrderStatus(orderStatus);
        order.setOrderUser(orderUser);
        return order;
    }

    private OrderStatus createOrderStatus(OrderUser orderUser) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreatedBy(orderUser.getUserName());
        orderStatus.setModifiedBy(orderUser.getUserName());
        return orderStatus;
    }

    private List<SpecialEquipmentOrder> createSpecialEquipmentOrder(List<SpecialEquipment> specialEquipmentList, OrderUser orderUser) {
        return specialEquipmentList.stream().map(specialEquipment -> createSpecialEquipmentOrder(specialEquipment, orderUser)).toList();
    }

    private SpecialEquipmentOrder createSpecialEquipmentOrder(SpecialEquipment specialEquipment, OrderUser orderUser) {
        var specialEquipmentOrder = new SpecialEquipmentOrder();
        specialEquipmentOrder.setSpecialEquipment(specialEquipment);
        specialEquipmentOrder.setCreatedBy(orderUser.getUserName());
        specialEquipmentOrder.setModifiedBy(orderUser.getUserName());
        specialEquipmentOrder.setOrderStatus(createOrderStatus(orderUser));
        return specialEquipmentOrder;
    }

    private CarRimOrder createCarRimOrder(CarRim carRim, OrderUser orderUser) {
        var carRimOrder = new CarRimOrder();
        carRimOrder.setCarRim(carRim);
        carRimOrder.setCreatedBy(orderUser.getUserName());
        carRimOrder.setModifiedBy(orderUser.getUserName());
        carRimOrder.setOrderStatus(createOrderStatus(orderUser));
        return carRimOrder;
    }

    private CarEngineOrder createCarEngineOrder(CarEngine carEngine, OrderUser orderUser) {
        var carEngineOrder = new CarEngineOrder();
        carEngineOrder.setCarEngine(carEngine);
        carEngineOrder.setCreatedBy(orderUser.getUserName());
        carEngineOrder.setModifiedBy(orderUser.getUserName());
        carEngineOrder.setOrderStatus(createOrderStatus(orderUser));
        return carEngineOrder;
    }

    private CarColorsOrder createCarColorOrder(CarColor carColor, OrderUser orderUser) {
        var carColorsOrder = new CarColorsOrder();
        carColorsOrder.setCarColor(carColor);
        carColorsOrder.setCreatedBy(orderUser.getUserName());
        carColorsOrder.setModifiedBy(orderUser.getUserName());
        carColorsOrder.setOrderStatus(createOrderStatus(orderUser));
        return carColorsOrder;
    }

    private List<SpecialEquipment> getSpecialeEquipments(List<String> specialEquipmentProductIds) {
        return specialEquipmentProductIds.stream().map(specialEquipmentService::getSpecialEquipmentByProductId).toList();
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return The OrderDto if found, otherwise null.
     */
    public Optional<OrderDto> getOrderById(String orderId) {
        // Find the entity by ID.
        Optional<Order> orderOptional = orderRepository.findByOrderId(orderId);
        if(orderOptional.isPresent()) {
            var orderDto = orderMapper.toDto(orderOptional.get());
            return Optional.ofNullable(orderDto);
        };
        return Optional.empty();
    }

    /**
     * Updates an existing order.
     *
     * @param orderId       The ID of the order to update.
     * @param orderDto The DTO with the updated data.
     */
    public void updateOrder(OrderUpdateDto orderDto) {
        // Check if the order exists before attempting to update.
        if (orderRepository.existsByOrderId(orderDto.getOrderId())) {
            //Order updatedOrder = orderMapper.toEntity(orderDto);
            // updatedOrder.setId(id); // Ensure the ID is set for the update operation.
            // orderRepository.save(updatedOrder);
        } else {
            // Throw an exception or handle the case where the order is not found.
            throw new OrderException("Order not found with id: " + orderDto.getOrderId());
        }
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    public void deleteOrder(Integer id) {
        // The repository handles the deletion.
        orderRepository.deleteById(id);

    }


}