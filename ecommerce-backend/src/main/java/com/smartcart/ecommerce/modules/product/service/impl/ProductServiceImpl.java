package com.smartcart.ecommerce.modules.product.service.impl;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.common.exceptions.CategoryNotPresentException;
import com.smartcart.ecommerce.common.exceptions.ProductAlreadyExistsException;
import com.smartcart.ecommerce.common.exceptions.ProductDoesNotExistsException;
import com.smartcart.ecommerce.common.services.FileService;
import com.smartcart.ecommerce.modules.category.model.Category;
import com.smartcart.ecommerce.modules.category.repository.CategoryRepository;
import com.smartcart.ecommerce.modules.product.dtos.CreateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.GetProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductImagesDto;
import com.smartcart.ecommerce.modules.product.enums.Gender;
import com.smartcart.ecommerce.modules.product.model.Product;
import com.smartcart.ecommerce.modules.product.repository.ProductRepository;
import com.smartcart.ecommerce.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {



    private final ProductRepository productRepository;

    private final ModelMapper mapper;
    private final FileService fileService;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public GenericResponseDto createProduct(CreateProductDto createProductDto) {

        if(productRepository.findByName(createProductDto.getName()).isPresent()){
            throw new ProductAlreadyExistsException("Product already Exists with name "+createProductDto.getName());
        }
        List<String> imagePaths=new ArrayList<>();
        for(MultipartFile file:createProductDto.getImages()){
            String path=fileService.storeImage(file);
            imagePaths.add(path);
        }

        Category category=categoryRepository.findById(createProductDto.getCategoryId()).orElseThrow(()->new CategoryNotPresentException("Category does not exists with id "+createProductDto.getCategoryId()));

        Product product=Product
                .builder()
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .images(imagePaths)
                .price(createProductDto.getPrice())
                .active(createProductDto.isActive())
                .category(category)
                .stockQuantity(createProductDto.getStockQuantity())
                .gender(createProductDto.getGender())
                .build();

        productRepository.save(product);

        return new GenericResponseDto("Product Created Successfully.");
    }

    @Override
    @Transactional
    public GenericResponseDto updateProduct(UpdateProductDto updateProductDto, Long productId) {
        Product product=getProductOrThrow(productId);
        mapper.map(updateProductDto,product);
        productRepository.save(product);
        return new GenericResponseDto("Product Updated Successfully.");
    }

    @Override
    @Transactional
    public GenericResponseDto updateStockQuantity(Long productId, int quantity) {
        Product product=getProductOrThrow(productId);
        product.setStockQuantity(product.getStockQuantity()+quantity);
        productRepository.save(product);
        return new GenericResponseDto("Stock Quantity Updated Successfully.");
    }

    @Override
    @Transactional
    public GenericResponseDto deleteProduct(Long productId) {
        Product product=getProductOrThrow(productId);
        productRepository.delete(product);
        return new GenericResponseDto("Product Deleted Successfully.");
    }

    @Override
    public Page<GetProductDto> getProducts(int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> pageOfProducts=productRepository.findAll(pageable);
        return pageOfProducts.map(p->mapper.map(p,GetProductDto.class));
    }

    @Override
    public Page<GetProductDto> getProductsByGender(String gender, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> pageOfProducts=productRepository.findByGender(getGender(gender),pageable);
        return pageOfProducts.map(p->mapper.map(p,GetProductDto.class));
    }

    @Override
    public Page<GetProductDto> getProductsBetweenRange(Long low, Long high, int page, int size) {
        Pageable pageable=PageRequest.of(page,size);
        Page<Product> pageOfProducts=productRepository.findByPriceBetween(low,high,pageable);
        return pageOfProducts.map(p->mapper.map(p, GetProductDto.class));
    }

    @Override
    @Transactional
    public GenericResponseDto updateProductImages(Long productId, UpdateProductImagesDto updateProductImagesDto) {
        Product product=getProductOrThrow(productId);
        if(updateProductImagesDto.getImages()!=null && !updateProductImagesDto.getImages().isEmpty()){
            List<String> imagePaths=new ArrayList<>();
            for(MultipartFile file:updateProductImagesDto.getImages()){
                String path=fileService.storeImage(file);
                imagePaths.add(path);
            }
            product.getImages().addAll(imagePaths);
            productRepository.save(product);

        }
        return new GenericResponseDto("Images Added Successfully.");
    }

    @Override
    @Transactional
    public GenericResponseDto deleteProductImage(Long productId, String imagePath) {
        Product product=getProductOrThrow(productId);
        if(imagePath!=null && !imagePath.isBlank() && product.getImages().contains(imagePath)){
            fileService.deleteImage(imagePath);
            product.getImages().remove(imagePath);
            productRepository.save(product);
        }
        return new GenericResponseDto("Image Deleted Successfully.");
    }

    private Gender getGender(String gender){
        try{
            return Gender.valueOf(gender.toUpperCase());
        }
        catch (IllegalArgumentException e){
            return Gender.OTHER;
        }

    }

    private Product getProductOrThrow(Long productId){
         return productRepository.findById(productId).orElseThrow(()->new ProductDoesNotExistsException("Product Not exists with id "+productId));
    }
}
