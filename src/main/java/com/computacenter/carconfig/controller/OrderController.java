package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.OrderUpdateDto;
import com.computacenter.carconfig.dto.OrderDto;
import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order/")
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param orderUpdateDto The OrderDto object containing order details from the request body.
     * @return A ResponseEntity containing a ResponseDto indicating success or failure.
     */
    @PostMapping(path = "/create", produces = "application/json", consumes = "application/json")
    public ResponseDto createOrder(@RequestBody OrderUpdateDto orderUpdateDto) {
        try {
            orderService.createOrderByIds(orderUpdateDto);
            return new ResponseDto("Order created successfully!", TransferStatus.SUCCESS);
        } catch (OrderException e) {
            return new ResponseDto("Failed to create order: " + e.getMessage(), TransferStatus.CANCELED);
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve, passed as a path variable.
     * @return A ResponseEntity containing the OrderDto if found, or a NOT_FOUND status.
     */
    @GetMapping(path = "/{orderId}", produces = "application/json")
    public OrderDto getOrder(@PathVariable String orderId) {
        Optional<OrderDto> order = orderService.getOrderById(orderId);
        return order.orElseGet(OrderDto::new);
    }

    /**
     * Updates an existing order.
     *
     * @param orderDto The OrderDto with updated details from the request body.
     * @return A ResponseEntity containing a ResponseDto indicating success or failure.
     */
    @PutMapping(path = "/update", produces = "application/json", consumes = "application/json")
    public ResponseDto updateOrder(@RequestBody OrderUpdateDto orderDto) {
        try {
            orderService.updateOrder(orderDto);
            return new ResponseDto("Order updated successfully!", TransferStatus.SUCCESS);
        } catch (Exception e) {
            return new ResponseDto("Failed to update order: " + e.getMessage(), TransferStatus.CANCELED);
        }
    }

    /**
     * Deletes an order by its ID.
     *
     * @param orderId The ID of the order to delete, passed as a path variable.
     * @return A ResponseEntity indicating the status of the deletion.
     */
    @DeleteMapping(path = "/delete/{orderId}")
    public ResponseDto deleteOrder(@PathVariable Integer orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseDto("Order deleted successfully!", TransferStatus.SUCCESS);
        } catch (Exception e) {
            return new ResponseDto("Failed to delete order: " + e.getMessage(), TransferStatus.CANCELED);
        }
    }
}

