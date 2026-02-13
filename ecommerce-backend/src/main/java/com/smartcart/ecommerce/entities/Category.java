package com.smartcart.ecommerce.entities;

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
