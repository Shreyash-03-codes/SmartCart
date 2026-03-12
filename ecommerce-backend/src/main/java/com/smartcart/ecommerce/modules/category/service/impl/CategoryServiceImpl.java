package com.smartcart.ecommerce.modules.category.service.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.CategoryExistsAlreadyException;
import com.smartcart.ecommerce.common.exceptions.CategoryNotPresentException;
import com.smartcart.ecommerce.common.services.FileService;
import com.smartcart.ecommerce.modules.category.dtos.CreateCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.GetCategoryDto;
import com.smartcart.ecommerce.modules.category.dtos.UpdateCategoryDto;
import com.smartcart.ecommerce.modules.category.model.Category;
import com.smartcart.ecommerce.modules.category.repository.CategoryRepository;
import com.smartcart.ecommerce.modules.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ModelMapper mapper;

    @Override
    public GenericResponseDto createCategory(CreateCategoryDto createCategoryDto) {

        if(categoryRepository.findByName(createCategoryDto.getName()).isPresent()){
            throw new CategoryExistsAlreadyException("Category already exists with name "+createCategoryDto.getName());
        }

        String imagePath=fileService.storeCategoryImage(createCategoryDto.getImage());

        Category category=Category
                .builder()
                .name(createCategoryDto.getName())
                .description(createCategoryDto.getDescription())
                .imagePath(imagePath)
                .products(new ArrayList<>())
                .build();

        categoryRepository.save(category);
        return GenericResponseDto
                .builder()
                .message("Category Created Successfully.")
                .build();
    }

    @Override
    public GenericResponseDto updateCategory(UpdateCategoryDto updateCategoryDto, Long categoryId) {

        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotPresentException("Category does not exists with id "+categoryId));

        if(updateCategoryDto.getName()!=null && !updateCategoryDto.getName().isBlank()){
            category.setName(updateCategoryDto.getName());
        }

        if(updateCategoryDto.getDescription()!=null && !updateCategoryDto.getDescription().isBlank()){
            category.setDescription(updateCategoryDto.getDescription());
        }

        if(updateCategoryDto.getImage()!=null && !updateCategoryDto.getImage().isEmpty()){
            String filePath=fileService.storeCategoryImage(updateCategoryDto.getImage());
            category.setImagePath(filePath);
        }
        categoryRepository.save(category);
        return GenericResponseDto
                .builder()
                .message("Category Updated Successfully.")
                .build();
    }

    @Override
    public GenericResponseDto deleteCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotPresentException("Category does not exists with id "+categoryId));
        if(category.getImagePath()!=null){
            fileService.deleteImage(category.getImagePath());
        }

        categoryRepository.delete(category);
        return GenericResponseDto
                .builder()
                .message("Category Deleted Successfully.")
                .build();
    }

    @Override
    public List<GetCategoryDto> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map((c)->mapper.map(c,GetCategoryDto.class))
                .toList();
    }

    @Override
    public GetCategoryDto getCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new CategoryNotPresentException("Category does not exists with id "+categoryId));
        return mapper.map(category,GetCategoryDto.class);
    }
}
