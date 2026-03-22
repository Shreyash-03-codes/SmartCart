package com.smartcart.ecommerce.modules.cart.controller;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.cart.dtos.GetCartItemDto;
import com.smartcart.ecommerce.modules.cart.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<Page<GetCartItemDto>> getCart(@RequestParam(defaultValue = "0",value = "pageIndex") int pageIndex,@RequestParam(defaultValue = "5",value = "pageSize") int pageSize){
        return ResponseEntity.ok(cartService.getCart(pageIndex,pageSize));
    }

    @PostMapping("/item/{productId}")
    public ResponseEntity<GenericResponseDto> addToCart(@PathVariable("productId") Long productId){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(productId));
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<GenericResponseDto> increaseCount(@PathVariable("cartItemId") Long cartItemId,@RequestParam("count") int count){
        return ResponseEntity.ok(cartService.updateCount(cartItemId,count,true));
    }

    @DeleteMapping(value = "/item/{cartItemId}",params = {"count"})
    public ResponseEntity<GenericResponseDto> decreaseCount(@PathVariable("cartItemId") Long cartItemId,@RequestParam("count") int count){
        return ResponseEntity.ok(cartService.updateCount(cartItemId,count,false));
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<GenericResponseDto> removeCartItem(@PathVariable("cartItemId") Long cartItemId){
        return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
    }
}
