package com.smartcart.ecommerce.modules.cart.model;

import com.smartcart.ecommerce.common.models.AuditEntity;
import com.smartcart.ecommerce.modules.product.model.Product;
import com.smartcart.ecommerce.modules.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(
        name = "cart_items",
        uniqueConstraints ={
                @UniqueConstraint(columnNames = {"user_id","product_id"})
        }
)
@Builder
public class CartItem extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
