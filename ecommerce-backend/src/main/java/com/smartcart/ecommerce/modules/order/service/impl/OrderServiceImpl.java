package com.smartcart.ecommerce.modules.order.service.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.OrderNotPresentException;
import com.smartcart.ecommerce.common.services.GetUserService;
import com.smartcart.ecommerce.modules.cart.model.CartItem;
import com.smartcart.ecommerce.modules.cart.repository.CartItemRepository;
import com.smartcart.ecommerce.modules.order.dtos.CreateOrderDto;
import com.smartcart.ecommerce.modules.order.dtos.GetOrderDto;
import com.smartcart.ecommerce.modules.order.dtos.GetOrderItemDto;
import com.smartcart.ecommerce.modules.order.enums.OrderStatus;
import com.smartcart.ecommerce.modules.order.model.Order;
import com.smartcart.ecommerce.modules.order.model.OrderItem;
import com.smartcart.ecommerce.modules.order.repository.OrderItemRepository;
import com.smartcart.ecommerce.modules.order.repository.OrderRepository;
import com.smartcart.ecommerce.modules.order.service.OrderService;
import com.smartcart.ecommerce.modules.payment.model.Payment;
import com.smartcart.ecommerce.modules.payment.repository.PaymentRepository;
import com.smartcart.ecommerce.modules.product.model.Product;
import com.smartcart.ecommerce.modules.product.repository.ProductRepository;
import com.smartcart.ecommerce.modules.user.model.User;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {


    private final OrderRepository orderRepository;

    private final GetUserService getUserService;

    private final CartItemRepository cartItemRepository;

    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public GenericResponseDto createOrder() {

        try{


        User user=getUserService.getUser();
        List<CartItem> cartItems=cartItemRepository.findAllByUser(user);
        BigDecimal amount=BigDecimal.ZERO;

        if(cartItems.isEmpty()){
            throw new RuntimeException("Cart is Empty.");
        }
        Order order=Order
                .builder()
                .orderStatus(OrderStatus.CREATED)
                .orderItems(new ArrayList<>())
                .amount(amount)
                .user(user)
                .build();


        for(CartItem ci:cartItems){
            OrderItem orderItem=OrderItem
                    .builder()
                    .product(ci.getProduct())
                    .price(ci.getProduct().getPrice())
                    .quantity(ci.getQuantity())
                    .build();

            Product product=ci.getProduct();
            if(product.getStockQuantity()< ci.getQuantity()){
                throw new RuntimeException("Insufficient Stock");
            }

            product.setStockQuantity(product.getStockQuantity()-ci.getQuantity());


            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
            amount=amount.add(ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())));
        }
        order.setAmount(amount);
        orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);

        return new GenericResponseDto("Order Created Successfully.");
        }
        catch (OptimisticLockException | ObjectOptimisticLockingFailureException e){
            throw new RuntimeException("Product Stock Changed Try Again.");
        }
    }

    @Override
    public Page<GetOrderDto> getOrders(int pageIndex, int pageSize) {

        User user=getUserService.getUser();
        Pageable pageable= PageRequest.of(pageIndex, pageSize);
        Page<Order> orders=orderRepository.findAllByUser(user,pageable);
        return orders
                .map(o->{
                    return GetOrderDto
                            .builder()
                            .id(o.getId())
                            .orderItems(o.getOrderItems().stream().map(ot->{
                                return GetOrderItemDto
                                        .builder()
                                        .price(ot.getPrice())
                                        .quantity(ot.getQuantity())
                                        .images(ot.getProduct().getImages())
                                        .productName(ot.getProduct().getName())
                                        .amount(ot.getPrice().multiply(BigDecimal.valueOf(ot.getQuantity())))
                                        .build();
                            }).toList()
                            )
                            .orderStatus(o.getOrderStatus())
                            .amount(o.getAmount())
                            .build();
                });
    }

    @Override
    public GetOrderDto getOrder(Long orderId) {
        User user=getUserService.getUser();
        Order order=orderRepository.findByIdAndUser(orderId,user).orElseThrow(()->new RuntimeException("Unauthorized Access."));
        return GetOrderDto
                .builder()
                .id(order.getId())
                .orderItems(order.getOrderItems().stream().map(ot->{
                    return GetOrderItemDto
                            .builder()
                            .productName(ot.getProduct().getName())
                            .productId(ot.getProduct().getId())
                            .images(ot.getProduct().getImages())
                            .price(ot.getPrice())
                            .amount(ot.getPrice().multiply(BigDecimal.valueOf(ot.getQuantity())))
                            .quantity(ot.getQuantity())
                            .build();
                }).toList()
                )
                .orderStatus(order.getOrderStatus())
                .amount(order.getAmount())
                .build();
    }

    @Override
    public Page<GetOrderDto> getOrderHistory(int pageIndex, int pageSize) {
        User user=getUserService.getUser();
        Pageable pageable= PageRequest.of(pageIndex, pageSize);
        Page<Order> orders=orderRepository.findAllByUserAndOrderStatus(user, OrderStatus.DELIVERED,pageable);
        return orders
                .map(o->{
                    return GetOrderDto
                            .builder()
                            .id(o.getId())
                            .orderItems(o.getOrderItems().stream().map(ot->{
                                        return GetOrderItemDto
                                                .builder()
                                                .price(ot.getPrice())
                                                .quantity(ot.getQuantity())
                                                .images(ot.getProduct().getImages())
                                                .productName(ot.getProduct().getName())
                                                .amount(ot.getPrice().multiply(BigDecimal.valueOf(ot.getQuantity())))
                                                .build();
                                    }).toList()
                            )
                            .orderStatus(o.getOrderStatus())
                            .amount(o.getAmount())
                            .build();
                });
    }

    @Override
    public Page<GetOrderDto> getOrdersForAdmin(int pageIndex, int pageSize) {
        Pageable pageable= PageRequest.of(pageIndex, pageSize);
        Page<Order> orders=orderRepository.findAll(pageable);
        return orders
                .map(o->{
                    return GetOrderDto
                            .builder()
                            .id(o.getId())
                            .orderItems(o.getOrderItems().stream().map(ot->{
                                        return GetOrderItemDto
                                                .builder()
                                                .price(ot.getPrice())
                                                .quantity(ot.getQuantity())
                                                .images(ot.getProduct().getImages())
                                                .productName(ot.getProduct().getName())
                                                .amount(ot.getPrice().multiply(BigDecimal.valueOf(ot.getQuantity())))
                                                .build();
                                    }).toList()
                            )
                            .orderStatus(o.getOrderStatus())
                            .amount(o.getAmount())
                            .build();
                });
    }

    @Override
    @Transactional
    public GenericResponseDto changeOrderStatus(Long orderId, String status) {
        Order order=orderRepository.findById(orderId).orElseThrow(()->new OrderNotPresentException("Order does not exists with id "+orderId));
        order.setOrderStatus(getOrderStatus(status));
        orderRepository.save(order);
        return new GenericResponseDto("Order Status Changes Successfully.");
    }

    private OrderStatus getOrderStatus(String status){
        return OrderStatus.valueOf(status);
    }
}
