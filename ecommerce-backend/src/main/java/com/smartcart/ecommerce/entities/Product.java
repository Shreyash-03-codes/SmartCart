package com.smartcart.ecommerce.entities;

import com.smartcart.ecommerce.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
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

    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(
            name = "product_images",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "image_path")
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    private Category category;
}
