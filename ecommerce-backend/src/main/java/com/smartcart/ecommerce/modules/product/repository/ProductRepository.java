package com.smartcart.ecommerce.modules.product.repository;

import com.smartcart.ecommerce.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
