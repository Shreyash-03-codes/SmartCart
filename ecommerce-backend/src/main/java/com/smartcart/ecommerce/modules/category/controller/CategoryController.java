package com.smartcart.ecommerce.modules.category.controller;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.category.dtos.CreateCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.GetCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.UpdateCategoryDto;
import com.smartcart.ecommerce.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseDto> createCategory(@ModelAttribute CreateCategoryDto createCategoryDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(createCategoryDto));
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseDto> updateCategory(@ModelAttribute UpdateCategoryDto updateCategoryDto,@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.updateCategory(updateCategoryDto,categoryId));
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseDto> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.deleteCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<GetCategoryDto>> getCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<GetCategoryDto> getCategory(@PathVariable("categoryId") Long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
    }


}
