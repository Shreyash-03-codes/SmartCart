package com.smartcart.ecommerce.modules.orderitem.repository;

import com.smartcart.ecommerce.modules.orderitem.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
