package com.smartcart.ecommerce.modules.order.repository;

import com.smartcart.ecommerce.modules.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
