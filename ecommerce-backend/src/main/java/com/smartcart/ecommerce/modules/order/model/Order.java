package com.smartcart.ecommerce.modules.order.model;

import com.smartcart.ecommerce.common.models.AuditEntity;
import com.smartcart.ecommerce.modules.payment.model.Payment;
import com.smartcart.ecommerce.modules.order.enums.OrderStatus;
import com.smartcart.ecommerce.modules.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@Table(name = "orders")
public class Order extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
