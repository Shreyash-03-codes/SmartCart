package com.smartcart.ecommerce.modules.cart.repository;

import com.smartcart.ecommerce.modules.cart.model.CartItem;
import com.smartcart.ecommerce.modules.product.model.Product;
import com.smartcart.ecommerce.modules.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Page<CartItem> findAllByUser(User user, Pageable pageable);

    List<CartItem> findAllByUser(User user);

    Optional<CartItem> findByIdAndUser(Long cartItemId, User user);

    Optional<CartItem> findByUserAndProduct(User user, Product product);
}
