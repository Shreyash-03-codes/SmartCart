package com.smartcart.ecommerce.entities;

import com.smartcart.ecommerce.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_product_category",columnList = "category_id"),
                @Index(name = "idx_product_gender",columnList = "gender")
        }
)
public class Product extends AuditEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
}
