package com.smartcart.ecommerce.modules.product.service;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.product.dtos.CreateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.GetProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductImagesDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    GenericResponseDto createProduct(CreateProductDto createProductDto);

    GenericResponseDto updateProduct(UpdateProductDto updateProductDto, Long productId);

    GenericResponseDto updateStockQuantity(Long productId, int quantity);

    GenericResponseDto deleteProduct(Long productId);

    Page<GetProductDto> getProducts(int page, int size);

    Page<GetProductDto> getProductsByGender(String gender, int page, int size);

    Page<GetProductDto> getProductsBetweenRange(Long low, Long high, int page, int size);

    GenericResponseDto updateProductImages(Long productId, UpdateProductImagesDto updateProductImagesDto);

    GenericResponseDto deleteProductImage(Long productId, String imagePath);
}
