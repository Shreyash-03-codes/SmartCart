package com.smartcart.ecommerce.modules.category.model;

import com.smartcart.ecommerce.common.models.AuditEntity;
import com.smartcart.ecommerce.modules.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    private String description;

    private String imagePath;

    @OneToMany(mappedBy = "category",orphanRemoval = true,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products;



}
