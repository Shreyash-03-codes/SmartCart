package com.smartcart.ecommerce.modules.order.controller;


import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.order.dtos.CreateOrderDto;
import com.smartcart.ecommerce.modules.order.dtos.GetOrderDto;
import com.smartcart.ecommerce.modules.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<GenericResponseDto> createOrder(){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder());
    }

    @GetMapping(params = {"pageIndex","pageSize"})
    public ResponseEntity<Page<GetOrderDto>> getOrders(@RequestParam(defaultValue = "0",value = "pageIndex") int pageIndex,@RequestParam(defaultValue = "5",value = "pageSize") int pageSize){
        return ResponseEntity.ok(orderService.getOrders(pageIndex,pageSize));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<GetOrderDto> getOrder(@PathVariable("orderId") Long orderId){
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/history")
    public ResponseEntity<Page<GetOrderDto>> getOrderHistory(@RequestParam(defaultValue = "0",value = "pageIndex") int pageIndex,@RequestParam(defaultValue = "5",value = "pageSize") int pageSize){
        return ResponseEntity.ok(orderService.getOrderHistory(pageIndex,pageSize));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<GetOrderDto>> getAllOrders(@RequestParam(defaultValue = "0",value = "pageIndex") int pageIndex,@RequestParam(defaultValue = "10",value = "pageSize") int pageSize){
        return ResponseEntity.ok(orderService.getOrdersForAdmin(pageIndex,pageSize));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/{orderId}/{status}")
    public ResponseEntity<GenericResponseDto> changeOrderStatus(@PathVariable("orderId") Long orderId,@PathVariable("status") String status){
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId,status));
    }


}
