package com.smartcart.ecommerce.modules.cart.services;

import com.smartcart.ecommerce.common.dtos.generic.GenericResponseDto;
import com.smartcart.ecommerce.modules.cart.dtos.GetCartItemDto;
import org.springframework.data.domain.Page;

public interface CartService {

    GenericResponseDto addToCart(Long productId);

    GenericResponseDto updateCount(Long cartItemId, int count, boolean add);

    GenericResponseDto removeCartItem(Long cartItemId);

    Page<GetCartItemDto> getCart(int pageIndex, int pageSize);
}
