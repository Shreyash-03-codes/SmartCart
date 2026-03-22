package com.smartcart.ecommerce.modules.order.repository;

import com.smartcart.ecommerce.modules.order.enums.OrderStatus;
import com.smartcart.ecommerce.modules.order.model.Order;
import com.smartcart.ecommerce.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);


    Optional<Order> findByIdAndUser(Long orderId, User user);

    Page<Order> findAllByUserAndOrderStatus(User user, OrderStatus orderStatus, Pageable pageable);
}
