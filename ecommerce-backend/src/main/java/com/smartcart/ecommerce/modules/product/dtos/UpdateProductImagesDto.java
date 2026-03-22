package com.smartcart.ecommerce.modules.product.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class UpdateProductImagesDto {
    private List<MultipartFile> images;
}
