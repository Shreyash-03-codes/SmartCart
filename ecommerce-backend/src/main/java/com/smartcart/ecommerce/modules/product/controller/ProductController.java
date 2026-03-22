package com.smartcart.ecommerce.modules.product.controller;


import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.product.dtos.CreateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.GetProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductDto;
import com.smartcart.ecommerce.modules.product.dtos.UpdateProductImagesDto;
import com.smartcart.ecommerce.modules.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenericResponseDto> createProduct(@ModelAttribute CreateProductDto createProductDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(createProductDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ResponseEntity<GenericResponseDto> updateProduct(@PathVariable("productId") Long productId,@ModelAttribute UpdateProductDto updateProductDto){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(updateProductDto,productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{productId}/{quantity}")
    public ResponseEntity<GenericResponseDto> updateStockProduct(@PathVariable("productId") Long productId,@PathVariable("quantity") int quantity){
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateStockQuantity(productId,quantity));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<GenericResponseDto> deleteProduct(@PathVariable("productId") Long productId){
        return ResponseEntity.status(HttpStatus.OK).body(productService.deleteProduct(productId));
    }


    @GetMapping
    public ResponseEntity<Page<GetProductDto>> getProducts(@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value="size",defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProducts(page,size));
    }

    @GetMapping(params = {"gender"})
    public ResponseEntity<Page<GetProductDto>> getProductsByGender(@RequestParam("gender") String gender,@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value="size",defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByGender(gender,page,size));
    }

    @GetMapping(params = {"low","high"})
    public ResponseEntity<Page<GetProductDto>> getProductsBetweenRange(@RequestParam("low") Long low,@RequestParam("high") Long high,@RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value="size",defaultValue = "10") int size){
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsBetweenRange(low,high,page,size));
    }

    @PutMapping("/images/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseDto> updateImages(@PathVariable("productId") Long productId, @ModelAttribute UpdateProductImagesDto updateProductImagesDto){
        return ResponseEntity.ok(productService.updateProductImages(productId,updateProductImagesDto));
    }

    @DeleteMapping("/images/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenericResponseDto> deleteImages(@PathVariable("productId") Long productId,@RequestParam("imagePath") String imagePath){
        return ResponseEntity.ok(productService.deleteProductImage(productId,imagePath));
    }

}
