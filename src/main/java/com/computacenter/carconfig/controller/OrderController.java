package com.computacenter.carconfig.controller;

import com.computacenter.carconfig.dto.OrderUpdateResponseDto;
import com.computacenter.carconfig.dto.order.CarOrderDto;
import com.computacenter.carconfig.dto.order.CarOrderUpdateDto;
import com.computacenter.carconfig.dto.ResponseDto;
import com.computacenter.carconfig.enums.TransferStatus;
import com.computacenter.carconfig.exceptions.OrderException;
import com.computacenter.carconfig.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    /**
     * Creates a new order.
     *
     * @param carOrderUpdateDto The CarOrderDto object containing order details from the request body.
     * @return A ResponseEntity containing a ResponseDto indicating success or failure.
     */
    @PostMapping(path = "/create", produces = "application/json", consumes = "application/json")
    public OrderUpdateResponseDto createOrder(@RequestBody CarOrderUpdateDto carOrderUpdateDto) {
        try {
            String orderId = orderService.createOrderByIds(carOrderUpdateDto);
            return new OrderUpdateResponseDto("CarOrder created successfully!", orderId, TransferStatus.SUCCESS);
        } catch (OrderException e) {
            return new OrderUpdateResponseDto("Failed to create order", TransferStatus.CANCELED, e.getMessage());
        }
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param orderId The ID of the order to retrieve, passed as a path variable.
     * @return A ResponseEntity containing the CarOrderDto if found, or a NOT_FOUND status.
     */
    @GetMapping(path = "/byId/{orderId}", produces = "application/json")
    public CarOrderDto getOrderByOrderId(@PathVariable String orderId) {
        Optional<CarOrderDto> order = orderService.getOrderById(orderId);
        return order.orElseGet(CarOrderDto::new);
    }

    /**
     * Updates an existing order.
     *
     * @param orderDto The CarOrderDto with updated details from the request body.
     * @return A ResponseEntity containing a ResponseDto indicating success or failure.
     */
    @PutMapping(path = "/update", produces = "application/json", consumes = "application/json")
    public OrderUpdateResponseDto updateOrder(@RequestBody CarOrderUpdateDto orderDto) {
        try {
            String orderId = orderService.updateOrder(orderDto);
            return new OrderUpdateResponseDto("CarOrder updated successfully!", orderId, TransferStatus.SUCCESS);
        } catch (Exception e) {
            return new OrderUpdateResponseDto("Failed to update order", TransferStatus.CANCELED, e.getMessage());
        }
    }

    /**
     * Deletes an order by its public orderId.
     *
     * @param orderId The public orderId of the order to delete, passed as a path variable.
     * @return A ResponseEntity indicating the status of the deletion.
     */
    @DeleteMapping(path = "/delete/{orderId}")
    public ResponseDto deleteOrder(@PathVariable String orderId) {
        try {
            orderService.deleteOrder(orderId);
            return new ResponseDto("CarOrder deleted successfully!", TransferStatus.SUCCESS);
        } catch (Exception e) {
            return new ResponseDto("Failed to delete order", TransferStatus.CANCELED, e.getMessage());
        }
    }
}

