package com.smartcart.ecommerce.modules.order.service;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.order.dtos.CreateOrderDto;
import com.smartcart.ecommerce.modules.order.dtos.GetOrderDto;
import org.springframework.data.domain.Page;

public interface OrderService {
    GenericResponseDto createOrder();

    Page<GetOrderDto> getOrders(int pageIndex, int pageSize);

    GetOrderDto getOrder(Long orderId);

    Page<GetOrderDto> getOrderHistory(int pageIndex, int pageSize);

    Page<GetOrderDto> getOrdersForAdmin(int pageIndex, int pageSize);

    GenericResponseDto changeOrderStatus(Long orderId, String status);
}
