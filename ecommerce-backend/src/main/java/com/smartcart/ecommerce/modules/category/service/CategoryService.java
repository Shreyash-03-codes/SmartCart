package com.smartcart.ecommerce.modules.category.service;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.category.dtos.CreateCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.GetCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.UpdateCategoryDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CategoryService {
    GenericResponseDto createCategory(CreateCategoryDto createCategoryDto);

    GenericResponseDto updateCategory(UpdateCategoryDto updateCategoryDto, Long categoryId);

    GenericResponseDto deleteCategory(Long categoryId);

    List<GetCategoryDto> getCategories();

    GetCategoryDto getCategory(Long categoryId);
}
